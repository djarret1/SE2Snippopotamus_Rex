package test;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.TagIndex;

class TagIndexWhenAddTag {
	private TagIndex tags;
	@BeforeEach
	void setUp() throws Exception {
		this.tags = new TagIndex();
	}

	@Test
	void testAddTagToValidTagIndex() {
		// No way to get at underlying data structure to test if tag was added
	}

}
