package com.pipipark.j.system.classscan.v2;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.pipipark.j.system.IPPPark;
import com.pipipark.j.system.annotation.PPPExclude;
import com.pipipark.j.system.annotation.PPPName;
import com.pipipark.j.system.classscan.v2.exception.PPPClassScanerException;
import com.pipipark.j.system.classscan.v2.util.Assert;
import com.pipipark.j.system.classscan.v2.util.ClassesLoader;
import com.pipipark.j.system.classscan.v2.util.PathMatchingResourcePatternResolver;
import com.pipipark.j.system.classscan.v2.util.Resource;
import com.pipipark.j.system.classscan.v2.util.ResourcePatternResolver;
import com.pipipark.j.system.core.PPPLogger;
import com.pipipark.j.system.core.PPPString;
import com.pipipark.j.system.exception.PPPServiceException;

/**
 * 第二版类查找(包含jar), 使用spring查找方式.
 * @author pipipark:cwj
 */
@SuppressWarnings({ "rawtypes", "serial" })
public final class PPPScan implements IPPPark {

	@SuppressWarnings("unused")
	private ClassesLoader loader = new ClassesLoader();
	private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

	/**
	 * 搜索所有类,包括jar包.
	 */
	public static Set<Class> doScan(String... basePackages) {
		Assert.notEmpty(basePackages,
				"At least one base package must be specified");
		Set<Class> beanDefinitions = new LinkedHashSet<Class>();
		PPPScan _me = new PPPScan();
		for (String basePackage : basePackages) {
			try {
				beanDefinitions
						.addAll(_me.findCandidateComponents(basePackage));
			} catch (IOException e) {
				throw new PPPClassScanerException("Class scan Exception!", e);
			}
		}
		return beanDefinitions;
	}

	/**
	 * 搜索scaner实现类,
	 * 根据scaner泛型获得对应类,并缓存起来.
	 */
	@SuppressWarnings("unchecked")
	public static void scaner(String... basePackages) {
		Set<Class> set = doScan(basePackages);
		for (Class<?> clazz : set) {
			if (!clazz.isInterface()
					&& !Modifier.isAbstract(clazz.getModifiers())) {
				if(PPPScaner.class.isAssignableFrom(clazz)){
					PPPName sn = clazz.getAnnotation(PPPName.class);
					String name;
					if(sn!=null && !PPPString.isRealEmpty(sn.value())){
						name = sn.value();
					}else{
						name = PPPString.lowFirst(PPPString.className(clazz));
					}
					PPPLogger.debug("scaner class: "+clazz.getName()+", name: "+name+".");
					PPPScaner scaner;
					try {
						scaner = (PPPScaner) clazz.newInstance();
						PPPScanerManager.set(name, scaner);
					} catch (InstantiationException | IllegalAccessException e) {
						throw new PPPClassScanerException("Class scan Exception!",
								e);
					}
				}
			}
		}
		PPPScaner[] scaners = PPPScanerManager.allScaner();
		for (Class clazz : set) {
			for (PPPScaner scaner : scaners) {
				if (!clazz.isInterface()
						&& !Modifier.isAbstract(clazz.getModifiers())) {
					Type type = scaner.type();
					if(type!=null && scaner instanceof PPPClassesScaner && ((Class)type).isAssignableFrom(clazz)){
						((PPPClassesScaner)scaner).set(clazz);
					}else if(type!=null && scaner instanceof PPPAnnotationScaner && clazz.getAnnotation(((Class)type)) != null){
						((PPPAnnotationScaner)scaner).set(clazz);
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private Set<Class> findCandidateComponents(String basePackage)
			throws IOException {
		Set<Class> candidates = new LinkedHashSet<Class>();
		if (StringUtils.isEmpty(basePackage)) {
			basePackage = "";
		}
		basePackage = basePackage.replace(".", "/");
		if (!basePackage.endsWith("/")) {
			basePackage += "/";
		}

		String packageSearchPath = "classpath*:" + basePackage + "**/*.class";
		Resource[] resources = resourcePatternResolver
				.getResources(packageSearchPath);
		for (Resource resource : resources) {
			if (resource.isReadable()) {
				String fileName = resource.getFilename();
				if (fileName.indexOf('$') != -1) {
					continue;
				}
				// Class clazz = loader.loadClass(fileName,
				// resource.getInputStream());
				Class clazz = null;
				String path = "";

				if (resource.exists()) {
					String p = resource.getURL().getPath();
					if (p.indexOf("!") >= 0) {
						p = p.substring(p.indexOf("!") + 2, p.length() - 6);
					} else {
						p = p.substring(p.indexOf("classes") + 8,
								p.length() - 6);
					}
					path = p.replace("/", ".");
				}
				try {
					clazz = Thread.currentThread().getContextClassLoader()
							.loadClass(path);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				if (clazz == null) {
					continue;
				}
				PPPExclude se = (PPPExclude)clazz.getAnnotation(PPPExclude.class);
				if(se!=null){
					continue;
				}
				candidates.add(clazz);
			}
		}
		return candidates;
	}

	public static void main(String[] args) throws PPPServiceException {
		PPPScan.doScan("com", "org");
	}
}
