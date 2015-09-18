package irlucene;

import org.junit.Test;

public class AAa {
	public enum fieldEnum{Title,Author,Workplace,Abstract,PublishTime};
	@Test
	public void printEnum()
	{
		System.out.println(fieldEnum.Title.ordinal());
	}
}
