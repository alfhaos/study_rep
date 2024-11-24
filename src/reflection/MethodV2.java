package reflection;

import reflection.data.BasicData;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodV2 {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        BasicData helloInstnce = new BasicData();
        helloInstnce.call(); // 코드를 변경하지 않는 이상 정적이다.


        // 동적 메소드 호출 - 리플렉션 사용
        Class<? extends BasicData> helloClass = helloInstnce.getClass();
        String methodName = "hello";

        // 메서드 이름을 변수로 변경할 수 있다.
        Method method1 = helloClass.getDeclaredMethod(methodName, String.class);
        Object returnValue = method1.invoke(helloInstnce, "hi");
        System.out.println("returnValue = " + returnValue);
    }
}
