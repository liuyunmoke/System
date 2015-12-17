package com.pipipark.j.system.testing.entity;

import com.google.gson.annotations.SerializedName;
import com.pipipark.j.system.entity.PPPEntity;
import com.pipipark.j.system.entity.SimplePPPEntity;
import com.pipipark.j.system.entity.field.PPPField;
import com.pipipark.j.system.entity.field.validate.FieldVaildater;
import com.pipipark.j.system.entity.field.validate.FieldValidateEnum;
import com.pipipark.j.system.entity.field.validate.annontation.FieldValidate;
import com.pipipark.j.system.exception.PPPServiceException;
import com.pipipark.j.system.testing.entity.event.TestEvent;

public class TestEntity extends SimplePPPEntity {
	
	private static final long serialVersionUID = -2351356234545685763L;

	@FieldValidate				//不能为空,但可以为空字符串.
	@SerializedName("name")		//别名.
	private String testName = "jhon";
	
	@FieldValidate({FieldValidateEnum.NotEmpty})
	private String testPassword = "123456";
	
	String sex;
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws PPPServiceException{
		TestEntity test = new TestEntity();
		//fields
		System.out.println("<---------------json----------------------");
		for (PPPField<?> f : test.fields()) {
			System.out.println(f.name()+":"+f.value());
		}
		System.out.println("==========================================>");
		System.out.println();
		
		//json
		System.out.println("<---------------json----------------------");
		System.out.println("old entity:");
		
		System.out.println(test.json());
		System.out.println("==========================================>");
		System.out.println();
		
		//event
		System.out.println("<---------------event----------------------");
		TestEvent eve = new TestEvent();
		test.addListener(eve);
		test.fireListener(eve);
		eve.add(2);
		test.fireListener(TestEvent.class);
		eve.add(5);
		test.fireAllListener();
		System.out.println("==========================================>");
		System.out.println();
		
		//clone
		System.out.println("<---------------clone----------------------");
		PPPEntity entity = test.cloneEntity();
		System.out.println("old entity:");
		System.out.println(test.json());
		
		String newPassword = "abcdefg";
		System.out.println("change password to: "+newPassword);
		entity.fieldValue("testPassword", newPassword);
		System.out.println("clone entity:");
		System.out.println(entity.json());
		System.out.println("==========================================>");
		System.out.println();
		
		//fieldValidate
		System.out.println("<--------------fieldValidate---------------");
		try{
			FieldVaildater.vaildate(test);
			System.out.println("validate success!");
		}catch(PPPServiceException e){
			System.out.println("validate failure!");
		}
		System.out.println("==========================================>");
		System.out.println();
		
		//entityCache
		System.out.println("<---------------entityCache----------------");
		test.refresh();
		System.out.println("==========================================>");
		System.out.println();
		
		test.field("sex");
	}

}
