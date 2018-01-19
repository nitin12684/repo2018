package com.jaba.eight.examples;

import static org.junit.Assert.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

import com.jaba.eight.examples.Dream.FREQUENCY;


/**
 * @author vfroot
 *
 */
public class DreamTest {

	@Test
	public void testGetKeyList() {
		List<Integer> lengthList = Dream.getDreamList().stream().filter(d -> (d.frequency == FREQUENCY.MONTHLY)).skip(1).limit(1)
				.map(Dream::getTitle).map(String::length).collect(Collectors.toList());
		Assert.assertEquals(1, lengthList.size());
	}

	@Test
	public void testGetDreamList() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetValueList() {
		fail("Not yet implemented");
	}

}
