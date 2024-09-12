import java.util.concurrent.RecursiveTask;

public class FactorialTask extends RecursiveTask<Long> {
    private final int number;

    // Пороговое значение, при котором будет происходить вычисление в обычном режиме
    private static final int THRESHOLD = 5;

    public FactorialTask(int number) {
        this.number = number;
    }

    @Override
    protected Long compute() {
        // Если число меньше или равно пороговому значению, вычисляем факториал прямо
        if (number <= THRESHOLD) {
            return factorial(number);
        }

        // Разделение задачи на подзадачи
        int mid = number / 2;
        FactorialTask lowerTask = new FactorialTask(mid);
        FactorialTask upperTask = new FactorialTask(number - mid);

        // Асинхронное выполнение подзадач
        lowerTask.fork();
        long upperResult = upperTask.compute(); // Вычисляем верхнюю часть
        long lowerResult = lowerTask.join(); // Получаем результат из нижней части

        // Возвращаем общий результат
        return lowerResult * upperResult;
    }

    // Метод для вычисления факториала
    private long factorial(int n) {
        long result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }



}
