package Exeptions;

public class MyInputException extends RuntimeException{
    public MyInputException() {

       super("\n\nИсчерпано количество попыток ввода\n");
        System.out.println("Вызываем System.exit(0); или Exception см. ниже");
    }
}
