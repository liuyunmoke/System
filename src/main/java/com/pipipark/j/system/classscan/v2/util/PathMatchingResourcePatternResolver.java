package com.pipipark.j.system.classscan.v2.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PathMatchingResourcePatternResolver implements ResourcePatternResolver {
	
	private final ResourceLoader resourceLoader = new DefaultResourceLoader();
	private static Method equinoxResolveMethod;

	static {
		try {
			Class<?> fileLocatorClass = ClassUtils.forName("org.eclipse.core.runtime.FileLocator",
					PathMatchingResourcePatternResolver.class.getClassLoader());
			equinoxResolveMethod = fileLocatorClass.getMethod("resolve", URL.class);
		}
		catch (Throwable ex) {
			equinoxResolveMethod = null;
		}
	}
	
	private PathMatcher pathMatcher = new AntPathMatcher();

	@Override
	public Resource getResource(String location) {
		return getResourceLoader().getResource(location);
	}
	@Override
	public ClassLoader getClassLoader() {
		return getResourceLoader().getClassLoader();
	}

	@Override
	public Resource[] getResources(String locationPattern) throws IOException {
		Assert.notNull(locationPattern, "Location pattern must not be null");
		if (locationPattern.startsWith(CLASSPATH_ALL_URL_PREFIX)) {
			// a class path resource (multiple resources for same name possible)
			if (getPathMatcher().isPattern(locationPattern.substring(CLASSPATH_ALL_URL_PREFIX.length()))) {
				// a class path resource pattern
				return findPathMatchingResources(locationPattern);
			}
			else {
				// all class path resources with the given name
				return findAllClassPathResources(locationPattern.substring(CLASSPATH_ALL_URL_PREFIX.length()));
			}
		}
		else {
			// Only look for a pattern after a prefix here
			// (to not get fooled by a pattern symbol in a strange prefix).
			int prefixEnd = locationPattern.indexOf(":") + 1;
			if (getPathMatcher().isPattern(locationPattern.substring(prefixEnd))) {
				// a file pattern
				return findPathMatchingResources(locationPattern);
			}
			else {
				// a single resource with the given name
				return new Resource[] {getResourceLoader().getResource(locationPattern)};
			}
		}
	}
	
	public ResourceLoader getResourceLoader() {
		return this.resourceLoader;
	}
	
	public PathMatcher getPathMatcher() {
		return this.pathMatcher;
	}
	
	protected Resource[] findAllClassPathResources(String location) throws IOException {
		String path = location;
		if (path.startsWith("/")) {
			path = path.substring(1);
		}
		Set<Resource> result = doFindAllClassPathResources(path);
		return result.toArray(new Resource[result.size()]);
	}
	
	protected Set<Resource> doFindAllClassPathResources(String path) throws IOException {
		Set<Resource> result = new LinkedHashSet<Resource>(16);
		ClassLoader cl = getClassLoader();
		Enumeration<URL> resourceUrls = (cl != null ? cl.getResources(path) : ClassLoader.getSystemResources(path));
		while (resourceUrls.hasMoreElements()) {
			URL url = resourceUrls.nextElement();
			result.add(convertClassLoaderURL(url));
		}
		if ("".equals(path)) {
			// The above result is likely to be incomplete, i.e. only containing file system references.
			// We need to have pointers to each of the jar files on the classpath as well...
			addAllClassLoaderJarRoots(cl, result);
		}
		return result;
	}
	
	protected void addAllClassLoaderJarRoots(ClassLoader classLoader, Set<Resource> result) {
		if (classLoader instanceof URLClassLoader) {
			try {
				for (URL url : ((URLClassLoader) classLoader).getURLs()) {
					if (ResourceUtils.isJarFileURL(url)) {
						try {
							UrlResource jarResource = new UrlResource(
									ResourceUtils.JAR_URL_PREFIX + url.toString() + ResourceUtils.JAR_URL_SEPARATOR);
							if (jarResource.exists()) {
								result.add(jarResource);
							}
						}
						catch (MalformedURLException ex) {
							ex.printStackTrace();
						}
					}
				}
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		if (classLoader != null) {
			try {
				addAllClassLoaderJarRoots(classLoader.getParent(), result);
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	protected Resource convertClassLoaderURL(URL url) {
		return new UrlResource(url);
	}
	
	protected boolean isJarResource(Resource resource) throws IOException {
		return ResourceUtils.isJarURL(resource.getURL());
	}
	
	protected Resource[] findPathMatchingResources(String locationPattern) throws IOException {
		String rootDirPath = determineRootDir(locationPattern);
		String subPattern = locationPattern.substring(rootDirPath.length());
		Resource[] rootDirResources = getResources(rootDirPath);
		Set<Resource> result = new LinkedHashSet<Resource>(16);
		for (Resource rootDirResource : rootDirResources) {
			rootDirResource = resolveRootDirResource(rootDirResource);
			if (rootDirResource.getURL().getProtocol().startsWith(ResourceUtils.URL_PROTOCOL_VFS)) {
				result.addAll(VfsResourceMatchingDelegate.findMatchingResources(rootDirResource, subPattern, getPathMatcher()));
			}
			else if (isJarResource(rootDirResource)) {
				result.addAll(doFindPathMatchingJarResources(rootDirResource, subPattern));
			}
			else {
				result.addAll(doFindPathMatchingFileResources(rootDirResource, subPattern));
			}
		}
		return result.toArray(new Resource[result.size()]);
	}
	
	protected Set<Resource> doFindPathMatchingFileResources(Resource rootDirResource, String subPattern)
			throws IOException {

		File rootDir;
		try {
			rootDir = rootDirResource.getFile().getAbsoluteFile();
		}
		catch (IOException ex) {
			return Collections.emptySet();
		}
		return doFindMatchingFileSystemResources(rootDir, subPattern);
	}
	
	protected Set<Resource> doFindMatchingFileSystemResources(File rootDir, String subPattern) throws IOException {
		Set<File> matchingFiles = retrieveMatchingFiles(rootDir, subPattern);
		Set<Resource> result = new LinkedHashSet<Resource>(matchingFiles.size());
		for (File file : matchingFiles) {
			result.add(new FileSystemResource(file));
		}
		return result;
	}
	
	protected Set<File> retrieveMatchingFiles(File rootDir, String pattern) throws IOException {
		if (!rootDir.exists()) {
			// Silently skip non-existing directories.
			return Collections.emptySet();
		}
		if (!rootDir.isDirectory()) {
			// Complain louder if it exists but is no directory.
			return Collections.emptySet();
		}
		if (!rootDir.canRead()) {
			return Collections.emptySet();
		}
		String fullPattern = StringUtils.replace(rootDir.getAbsolutePath(), File.separator, "/");
		if (!pattern.startsWith("/")) {
			fullPattern += "/";
		}
		fullPattern = fullPattern + StringUtils.replace(pattern, File.separator, "/");
		Set<File> result = new LinkedHashSet<File>(8);
		doRetrieveMatchingFiles(fullPattern, rootDir, result);
		return result;
	}
	
	protected void doRetrieveMatchingFiles(String fullPattern, File dir, Set<File> result) throws IOException {
		File[] dirContents = dir.listFiles();
		if (dirContents == null) {
			return;
		}
		for (File content : dirContents) {
			String currPath = StringUtils.replace(content.getAbsolutePath(), File.separator, "/");
			if (content.isDirectory() && getPathMatcher().matchStart(fullPattern, currPath + "/")) {
				if (content.canRead()) {
					doRetrieveMatchingFiles(fullPattern, content, result);
				}
			}
			if (getPathMatcher().match(fullPattern, currPath)) {
				result.add(content);
			}
		}
	}
	
	protected Set<Resource> doFindPathMatchingJarResources(Resource rootDirResource, String subPattern)
			throws IOException {

		URLConnection con = rootDirResource.getURL().openConnection();
		JarFile jarFile;
		String jarFileUrl;
		String rootEntryPath;
		boolean newJarFile = false;

		if (con instanceof JarURLConnection) {
			// Should usually be the case for traditional JAR files.
			JarURLConnection jarCon = (JarURLConnection) con;
			ResourceUtils.useCachesIfNecessary(jarCon);
			jarFile = jarCon.getJarFile();
			jarFileUrl = jarCon.getJarFileURL().toExternalForm();
			JarEntry jarEntry = jarCon.getJarEntry();
			rootEntryPath = (jarEntry != null ? jarEntry.getName() : "");
		}
		else {
			// No JarURLConnection -> need to resort to URL file parsing.
			// We'll assume URLs of the format "jar:path!/entry", with the protocol
			// being arbitrary as long as following the entry format.
			// We'll also handle paths with and without leading "file:" prefix.
			String urlFile = rootDirResource.getURL().getFile();
			int separatorIndex = urlFile.indexOf(ResourceUtils.JAR_URL_SEPARATOR);
			if (separatorIndex != -1) {
				jarFileUrl = urlFile.substring(0, separatorIndex);
				rootEntryPath = urlFile.substring(separatorIndex + ResourceUtils.JAR_URL_SEPARATOR.length());
				jarFile = getJarFile(jarFileUrl);
			}
			else {
				jarFile = new JarFile(urlFile);
				jarFileUrl = urlFile;
				rootEntryPath = "";
			}
			newJarFile = true;
		}

		try {
			if (!"".equals(rootEntryPath) && !rootEntryPath.endsWith("/")) {
				// Root entry path must end with slash to allow for proper matching.
				// The Sun JRE does not return a slash here, but BEA JRockit does.
				rootEntryPath = rootEntryPath + "/";
			}
			Set<Resource> result = new LinkedHashSet<Resource>(8);
			for (Enumeration<JarEntry> entries = jarFile.entries(); entries.hasMoreElements();) {
				JarEntry entry = entries.nextElement();
				String entryPath = entry.getName();
				if (entryPath.startsWith(rootEntryPath)) {
					String relativePath = entryPath.substring(rootEntryPath.length());
					if (getPathMatcher().match(subPattern, relativePath)) {
						result.add(rootDirResource.createRelative(relativePath));
					}
				}
			}
			return result;
		}
		finally {
			// Close jar file, but only if freshly obtained -
			// not from JarURLConnection, which might cache the file reference.
			if (newJarFile) {
				jarFile.close();
			}
		}
	}
	
	protected JarFile getJarFile(String jarFileUrl) throws IOException {
		if (jarFileUrl.startsWith(ResourceUtils.FILE_URL_PREFIX)) {
			try {
				return new JarFile(ResourceUtils.toURI(jarFileUrl).getSchemeSpecificPart());
			}
			catch (URISyntaxException ex) {
				return new JarFile(jarFileUrl.substring(ResourceUtils.FILE_URL_PREFIX.length()));
			}
		}
		else {
			return new JarFile(jarFileUrl);
		}
	}
	
	protected String determineRootDir(String location) {
		int prefixEnd = location.indexOf(":") + 1;
		int rootDirEnd = location.length();
		while (rootDirEnd > prefixEnd && getPathMatcher().isPattern(location.substring(prefixEnd, rootDirEnd))) {
			rootDirEnd = location.lastIndexOf('/', rootDirEnd - 2) + 1;
		}
		if (rootDirEnd == 0) {
			rootDirEnd = prefixEnd;
		}
		return location.substring(0, rootDirEnd);
	}
	
	protected Resource resolveRootDirResource(Resource original) throws IOException {
		if (equinoxResolveMethod != null) {
			URL url = original.getURL();
			if (url.getProtocol().startsWith("bundle")) {
				return new UrlResource((URL) ReflectionUtils.invokeMethod(equinoxResolveMethod, null, url));
			}
		}
		return original;
	}
	private static class VfsResourceMatchingDelegate {

		public static Set<Resource> findMatchingResources(
				Resource rootResource, String locationPattern, PathMatcher pathMatcher) throws IOException {
			Object root = VfsPatternUtils.findRoot(rootResource.getURL());
			PatternVirtualFileVisitor visitor =
					new PatternVirtualFileVisitor(VfsPatternUtils.getPath(root), locationPattern, pathMatcher);
			VfsPatternUtils.visit(root, visitor);
			return visitor.getResources();
		}
	}
	
	@SuppressWarnings("unused")
	private static class PatternVirtualFileVisitor implements InvocationHandler {

		private final String subPattern;

		private final PathMatcher pathMatcher;

		private final String rootPath;

		private final Set<Resource> resources = new LinkedHashSet<Resource>();

		public PatternVirtualFileVisitor(String rootPath, String subPattern, PathMatcher pathMatcher) {
			this.subPattern = subPattern;
			this.pathMatcher = pathMatcher;
			this.rootPath = (rootPath.length() == 0 || rootPath.endsWith("/") ? rootPath : rootPath + "/");
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			String methodName = method.getName();
			if (Object.class.equals(method.getDeclaringClass())) {
				if (methodName.equals("equals")) {
					// Only consider equal when proxies are identical.
					return (proxy == args[0]);
				}
				else if (methodName.equals("hashCode")) {
					return System.identityHashCode(proxy);
				}
			}
			else if ("getAttributes".equals(methodName)) {
				return getAttributes();
			}
			else if ("visit".equals(methodName)) {
				visit(args[0]);
				return null;
			}
			else if ("toString".equals(methodName)) {
				return toString();
			}

			throw new IllegalStateException("Unexpected method invocation: " + method);
		}

		public void visit(Object vfsResource) {
			if (this.pathMatcher.match(this.subPattern,
					VfsPatternUtils.getPath(vfsResource).substring(this.rootPath.length()))) {
				this.resources.add(new VfsResource(vfsResource));
			}
		}

		public Object getAttributes() {
			return VfsPatternUtils.getVisitorAttribute();
		}

		public Set<Resource> getResources() {
			return this.resources;
		}

		public int size() {
			return this.resources.size();
		}

		@Override
		public String toString() {
			return "sub-pattern: " + this.subPattern + ", resources: " + this.resources;
		}
	}
}
