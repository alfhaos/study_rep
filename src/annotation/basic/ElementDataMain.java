package annotation.basic;

import java.util.Arrays;

public class ElementDataMain {

    public static void main(String[] args) {
        Class<ElementData1> annoClass = ElementData1.class;
        AnnoElement annoElement = annoClass.getAnnotation(AnnoElement.class);

        String value = annoElement.value();
        System.out.println("value = " + value);

        int count = annoElement.count();
        System.out.println("count = " + count);

        String[] tags = annoElement.tags();
        System.out.println("tags = " + Arrays.toString(tags));
    }
}
