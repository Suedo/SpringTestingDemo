package functional.chaining;

import com.example.testingdemo.service.UserService;

import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;


abstract class ChainWrapper {
    public Integer specification;
    public Integer resultValue;
}

public class Test {

    private final UserService userService;

    public Test(UserService userService) {this.userService = userService;}

    public static Function<Integer, String> job1 = num -> {
//        if (num % 2 == 0) throw new IllegalArgumentException("Num " + num + " cannot be a multiple of 2");
        //else

            return String.valueOf(num);
    };


    public static Function<String, Double> job2 = num -> {
//        if (Integer.parseInt(num) % 7 == 0)
//            throw new IllegalArgumentException("Num " + num + " cannot be a multiple of 7");
//        else
            return Double.valueOf(num);
    };

    public static Function<Double, String> job3 = num -> {
//        if (num.intValue() % 7 == 0) throw new IllegalArgumentException("Num " + num + " cannot be a multiple of 7");
//        else
            return String.valueOf(num);
    };


    //    public static <T> Function<T, T> applyOneAfterTheOther(List<Function<T, T>> transforms) {
    //        return transforms.stream().reduce(Function::andThen).orElse(Function.identity());
    //    }

    public static Function applyOneAfterTheOther(List<Function> transforms) {
        return transforms.stream().reduce(Function::andThen).orElse(Function.identity());
    }

    //    public static int no2s(int num) throws IllegalArgumentException {
    //        if (num % 2 == 0) throw new IllegalArgumentException("Num " + num + " cannot be a multiple of 2");
    //        else return num;
    //    }
    //
    //    public static int no7s(int num) throws IllegalArgumentException {
    //        if (num % 7 == 0) throw new IllegalArgumentException("Num " + num + " cannot be a multiple of 7");
    //        else return num;
    //    }


    public static void main(String[] args) {

        List<Function> jobs = List.of(Test.job1, Test.job2, Test.job3);
        Function jobsPipeline = Test.applyOneAfterTheOther(jobs);
        List<Integer> primes = List.of(2, 3, 5, 7, 11, 13,
                                       17, 19, 23, 29, 31, 37, 41, 43,
                                       47, 53, 59, 61, 67, 71, 73, 79,
                                       83, 89, 97);

        IntStream.range(1, 100)
                 .boxed()
                 .map(jobsPipeline)
                 .forEach(each -> System.out.println("Allowed: " + each));

    }
}
