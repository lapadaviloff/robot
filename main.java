public class main {
    //интерфейс для управления роботом
    interface RobotConnection extends AutoCloseable{
        void moveRobotTo(int x, int y);
        @Override
        void close();
    }
    //интерфейс для копирования исключений
    interface Dublicated{
        public Dublicated dublic();
    }
    //класc исключения RobotConnectionException при подключении к роботу
   public static  class RobotConnectionException extends RuntimeException implements Dublicated {
        public RobotConnectionException() {
            super();
        }
        public RobotConnectionException(String message) {
            super(message);

        }

        public RobotConnectionException(String message, Throwable cause) {
            super(message, cause);
        }
        @Override
        public Dublicated dublic(){
            return new RobotConnectionException();
        }
    }
    //класc исключения отличный от RobotConnectionException при подключении к роботу
  public static   class MyException extends RuntimeException implements Dublicated {
        public MyException() {
            super();
        }
        @Override
        public Dublicated dublic() {
            return new MyException();
        }
    }
    //основной класс входа в программу
    public static class MyClass {
        static int i; // переменная для перебора попыток полключений, что бы подключиться 3 раза
        static int repeatNumber; // переменная для перебора 27 вариантов различных ошибок из массива класса Test

        static public int getRepeatNumber() {
            return repeatNumber;
        }

        static public int getI() {
            return i;
        }

        public static void main(String args[]) {
            int toX = 1;
            int toY = 2;
            RobotConnectionManager robotConnectionManager = new RobotConnectionManager();
            for (repeatNumber = 0; repeatNumber < 26; repeatNumber++) {
                System.out.println("\r" + "repeat number: " + repeatNumber);
                StringBuilder builder = new StringBuilder();
                String[] names = {"RCM: ", "RC: ", "Close: "};
                int i = 0;
                for (String name : names) {
                    builder.append(name).append(Test.getTest(i) != null ? Test.getTest(i).toString() : "OK").append("||");
                    i++;
                }
                System.out.println("Exeption List: " + builder.toString());
                try {
                    moveRobot(robotConnectionManager, toX, toY); // запуск метода для решения задачи
                } catch (Throwable t) {
                    System.out.println("Exeption catch in big cicle");
                }
            }
        }

        //метод который мы создаем для управления роботом (то что нужно для решения задачи)
        static void moveRobot(RobotConnectionManager robotConnectionManager, int toX, int toY) {
//...
                     for (int i=0; i<3;i++){
                             RobotConnection robotConnection = robotConnectionManager.getConnection();
                             try{
                                 robotConnection.moveRobotTo(toX, toY);
                                 i=3;
                             } catch(RobotConnectionException e){

                                 if(i==2){
                                     throw new RobotConnectionException(e.getMessage(), e);
                                 }
                             }
                             catch(Exception t){
                                 throw t;
                             }
           }
//...
//...
        }

        //класс создает объект для управления роботом
//ошибка в соответсвии с массивом класса test выбрасываеться в 1 попытку подключения
        public static class RobotConnectionManager {
            RobotConnection getConnection() {
                System.out.println("attempt number: " + MyClass.getI() + ". start RCM");
                if ((Test.getTest(0) instanceof Throwable) && (MyClass.getI() == 1)) {
                    System.out.println("RCM Exeption");
                    throw Test.getTest(0);
                } else {
                    System.out.println("RCM OK");
                    return new RobotUse();
                }
            }
        }

        //класс управляет роботом
//ошибка в соответсвии с массивом класса test выбрасываеться в 1 попытку подключения
        public static class RobotUse implements RobotConnection {
            @Override
            public void moveRobotTo(int x, int y) {
                System.out.println("attempt number: " + MyClass.getI() + ". start RC");
                if ((Test.getTest(1) instanceof Throwable) && (MyClass.getI() == 1)) {
                    System.out.println("RC Exeption");
                    throw Test.getTest(1);
                } else {
                    System.out.println("Move robot OK");
                }
            }

            @Override
            public void close() {
                System.out.println("attempt number: " + MyClass.getI() + ". start close");
                if ((Test.getTest(2) instanceof Throwable) && (MyClass.getI() == 1)) {
                    System.out.println("Close Exeption");
                    throw Test.getTest(2);
                } else {
                    System.out.println("Close OK");
                }
            }
        }

        // класс массива ошибок, 27 вариантов размещений из 2 ошибок в 3 классах программы
       public static  class Test {
            static RuntimeException[] ExceptionArray = {null, new RobotConnectionException(), new MyException()};

            static RuntimeException getTest(int args) {
                int i = 0;
                RuntimeException[][] testArray = new RuntimeException[27][3];
                for (RuntimeException k : ExceptionArray) {
                    for (RuntimeException t : ExceptionArray) {
                        for (RuntimeException s : ExceptionArray) {
                            testArray[i][0] = k != null ? (RuntimeException) ((Dublicated) k).dublic() : null;
                            testArray[i][1] = t != null ? (RuntimeException) ((Dublicated) t).dublic() : null;
                            testArray[i][2] = s != null ? (RuntimeException) ((Dublicated) s).dublic() : null;
                            i++;
                        }
                    }
                }
                return testArray[MyClass.getRepeatNumber()][args];
            }
        }
    }
}