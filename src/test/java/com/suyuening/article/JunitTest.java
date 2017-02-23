package com.suyuening.article;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.suyuening.article.bean.DocBookSection;

import junit.framework.Assert;

public class JunitTest {

	@Test
	public void testJunitTset() {
		Assert.assertEquals(true, true);

		List<String> paraList = Lists.asList("一", new String[] { "二", "三" });
		DocBookSection dbs = new DocBookSection("测试文章");
		dbs.addPara("四");
		dbs.addPara("五");
		dbs.addMutiParas(paraList);
		dbs.printInfo();
	}

	@Test
    public void test_AutoUnboxing() {
    	Integer f1 = 100, f2 = 100, f3 = 150, f4 = 150;
    	Assert.assertEquals(false, (f1 == f2));
    	Assert.assertEquals(false, (f3 == f4));
    }
}
