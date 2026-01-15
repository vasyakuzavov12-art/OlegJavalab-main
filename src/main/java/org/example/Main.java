package org.example;

import tasks.*;
import geometry2d.*;
import geometry2d.exceptions.*;
import geometry3d.*;
import geometry3d.exceptions.*;
import collections.*;
import utils.StringProcessor;
import json.JsonStreamDemo;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;

public class Main extends Application {

    private static boolean guiMode = false;
    private static Stage primaryStage;
    private static Scanner scanner;

    public static void main(String[] args) {
        System.setProperty("console.encoding", "UTF-8");
        System.setProperty("file.encoding", "UTF-8");

        if (args.length > 0 && args[0].equals("--gui")) {
            guiMode = true;
            launch(args);
        } else {
            runConsoleApplication();
        }
    }

    @Override
    public void start(Stage stage) {
        primaryStage = stage;

        if (!guiMode) {
            Platform.exit();
            return;
        }

        showMainMenu();
    }

    private static void showMainMenu() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #f0f8ff, #e6f7ff);");

        Label title = new Label("УЧЕБНЫЙ ПРОЕКТ - ГЛАВНОЕ МЕНЮ");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0.5, 0, 1);");
        title.setPadding(new Insets(0, 0, 20, 0));

        // Создаем разделы
        Label section1 = new Label("ОСНОВНЫЕ ЗАДАНИЯ GUI:");
        section1.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #3498db; -fx-padding: 10 0 5 0;");

        String[] tasks = {
                "1. Перекидыватель слов",
                "2. Скрытие/показ виджетов",
                "3. Заказ в ресторане",
                "4. Калькулятор",
                "5. Текстовый флаг",
                "6. Рисование геометрических фигур",
                "7. Drag & Drop фигур"
        };

        VBox tasksBox = new VBox(5);
        for (String task : tasks) {
            Button btn = createStyledButton(task);
            btn.setOnAction(e -> openTask(Integer.parseInt(task.split("\\.")[0])));
            tasksBox.getChildren().add(btn);
        }

        Label section2 = new Label("КОНСОЛЬНЫЕ ЗАДАНИЯ:");
        section2.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #e74c3c; -fx-padding: 10 0 5 0;");

        String[] consoleTasks = {
                "8. Кнопка (MyButton)",
                "9. Весы (Balance)",
                "10. Колокол (Bell)",
                "11. Разделитель четных/нечетных",
                "12. Таблица (MyTable)",
                "13. Геометрические фигуры",
                "14. Работа с Collections API",
                "15. Генератор простых чисел",
                "16. Класс Human и коллекции",
                "17. Частота слов в тексте",
                "18. Инверсия Map<K, V>",
                "19. Демонстрация потоков данных"
        };

        VBox consoleBox = new VBox(5);
        for (String task : consoleTasks) {
            Button btn = createStyledButton(task, "#ecf0f1", "#7f8c8d");
            btn.setOnAction(e -> openTask(Integer.parseInt(task.split("\\.")[0])));
            consoleBox.getChildren().add(btn);
        }

        Button exitButton = createStyledButton("0. ВЫХОД ИЗ ПРОГРАММЫ", "#ff6b6b", "#c0392b");
        exitButton.setOnAction(e -> Platform.exit());

        // Создаем скроллируемую панель
        ScrollPane scrollPane = new ScrollPane();
        VBox content = new VBox(10);
        content.setPadding(new Insets(10));
        content.getChildren().addAll(title, section1, tasksBox, section2, consoleBox, exitButton);
        scrollPane.setContent(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        root.getChildren().add(scrollPane);

        Scene scene = new Scene(root, 500, 600);
        primaryStage.setTitle("Учебный проект - GUI режим");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private static Button createStyledButton(String text) {
        return createStyledButton(text, "#3498db", "#2980b9");
    }

    private static Button createStyledButton(String text, String color1, String color2) {
        Button btn = new Button(text);
        btn.setPrefWidth(400);
        btn.setPrefHeight(40);
        btn.setStyle(String.format(
                "-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: white; " +
                        "-fx-background-color: linear-gradient(to bottom, %s, %s); " +
                        "-fx-border-radius: 5px; -fx-background-radius: 5px; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0.5, 0, 2); " +
                        "-fx-cursor: hand;",
                color1, color2
        ));

        // Эффект при наведении
        btn.setOnMouseEntered(e ->
                btn.setStyle(String.format(
                        "-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: white; " +
                                "-fx-background-color: linear-gradient(to bottom, %s, %s); " +
                                "-fx-border-radius: 5px; -fx-background-radius: 5px; " +
                                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 8, 0.7, 0, 3); " +
                                "-fx-cursor: hand;",
                        color2, color1
                ))
        );

        btn.setOnMouseExited(e ->
                btn.setStyle(String.format(
                        "-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: white; " +
                                "-fx-background-color: linear-gradient(to bottom, %s, %s); " +
                                "-fx-border-radius: 5px; -fx-background-radius: 5px; " +
                                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0.5, 0, 2); " +
                                "-fx-cursor: hand;",
                        color1, color2
                ))
        );

        return btn;
    }

    private static void openTask(int taskNumber) {
        switch (taskNumber) {
            case 1:
                showWordSwitcher();
                break;
            case 2:
                showWidgetVisibility();
                break;
            case 3:
                showRestaurantOrder();
                break;
            case 4:
                showCalculator();
                break;
            case 5:
                showTextFlag();
                break;
            case 6:
                showGeometryDrawing();
                break;
            case 7:
                showGeometryDragDrop();
                break;
            case 8:
                showMyButtonDemo();
                break;
            case 9:
                showBalanceDemo();
                break;
            case 10:
                showBellDemo();
                break;
            case 11:
                showOddEvenSeparatorDemo();
                break;
            case 12:
                showMyTableDemo();
                break;
            case 13:
                showGeometryConsole();
                break;
            case 14:
                showCollectionsDemo();
                break;
            case 15:
                showPrimesGeneratorDemo();
                break;
            case 16:
                showHumanCollectionsDemo();
                break;
            case 17:
                showWordFrequencyDemo();
                break;
            case 18:
                showMapInversionDemo();
                break;
            case 19:
                showJsonStreamDemo();
                break;
        }
    }

    // ========== ЗАДАНИЕ 1: Перекидыватель слов ==========
    private static void showWordSwitcher() {
        Stage stage = new Stage();
        stage.setTitle("Задание 1: Перекидыватель слов");
        stage.setResizable(false);

        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #ffffff, #f8f9fa);");

        Label title = new Label("ПЕРЕКИДЫВАТЕЛЬ СЛОВ");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Первое поле
        VBox field1Box = new VBox(5);
        Label label1 = new Label("Первое поле:");
        label1.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        TextField field1 = new TextField();
        field1.setPromptText("Введите текст здесь...");
        field1.setPrefWidth(250);
        field1.setPrefHeight(40);
        field1.setStyle("-fx-font-size: 16px; -fx-background-radius: 5px; -fx-border-radius: 5px;");
        field1Box.getChildren().addAll(label1, field1);

        // Кнопка со стрелкой
        Button switchButton = new Button("→");
        switchButton.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-min-width: 80px; -fx-min-height: 80px; " +
                "-fx-background-color: linear-gradient(to bottom, #3498db, #2980b9); " +
                "-fx-text-fill: white; -fx-background-radius: 40px; -fx-border-radius: 40px; " +
                "-fx-effect: dropshadow(gaussian, rgba(52,152,219,0.5), 10, 0.5, 0, 3);");

        // Второе поле
        VBox field2Box = new VBox(5);
        Label label2 = new Label("Второе поле:");
        label2.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        TextField field2 = new TextField();
        field2.setPrefWidth(250);
        field2.setPrefHeight(40);
        field2.setStyle("-fx-font-size: 16px; -fx-background-radius: 5px; -fx-border-radius: 5px;");
        field2Box.getChildren().addAll(label2, field2);

        // Флаг направления
        final boolean[] forward = {true};

        // Обработчик кнопки
        switchButton.setOnAction(e -> {
            if (forward[0]) {
                if (!field1.getText().isEmpty()) {
                    field2.setText(field1.getText());
                    field1.clear();
                    switchButton.setText("←");
                    switchButton.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-min-width: 80px; -fx-min-height: 80px; " +
                            "-fx-background-color: linear-gradient(to bottom, #e74c3c, #c0392b); " +
                            "-fx-text-fill: white; -fx-background-radius: 40px; -fx-border-radius: 40px; " +
                            "-fx-effect: dropshadow(gaussian, rgba(231,76,60,0.5), 10, 0.5, 0, 3);");
                }
            } else {
                if (!field2.getText().isEmpty()) {
                    field1.setText(field2.getText());
                    field2.clear();
                    switchButton.setText("→");
                    switchButton.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-min-width: 80px; -fx-min-height: 80px; " +
                            "-fx-background-color: linear-gradient(to bottom, #3498db, #2980b9); " +
                            "-fx-text-fill: white; -fx-background-radius: 40px; -fx-border-radius: 40px; " +
                            "-fx-effect: dropshadow(gaussian, rgba(52,152,219,0.5), 10, 0.5, 0, 3);");
                }
            }
            forward[0] = !forward[0];
        });

        // Кнопка сброса
        Button resetButton = new Button("Сброс");
        resetButton.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-pref-width: 100px; -fx-pref-height: 40px; " +
                "-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-background-radius: 5px;");
        resetButton.setOnAction(e -> {
            field1.clear();
            field2.clear();
            forward[0] = true;
            switchButton.setText("→");
            switchButton.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-min-width: 80px; -fx-min-height: 80px; " +
                    "-fx-background-color: linear-gradient(to bottom, #3498db, #2980b9); " +
                    "-fx-text-fill: white; -fx-background-radius: 40px; -fx-border-radius: 40px; " +
                    "-fx-effect: dropshadow(gaussian, rgba(52,152,219,0.5), 10, 0.5, 0, 3);");
        });

        HBox fieldsBox = new HBox(20);
        fieldsBox.setAlignment(Pos.CENTER);
        fieldsBox.getChildren().addAll(field1Box, switchButton, field2Box);

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);

        Button backButton = new Button("Назад в меню");
        backButton.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-pref-width: 150px; -fx-pref-height: 40px; " +
                "-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-background-radius: 5px;");
        backButton.setOnAction(e -> stage.close());

        buttonBox.getChildren().addAll(resetButton, backButton);

        root.getChildren().addAll(title, fieldsBox, buttonBox);

        Scene scene = new Scene(root, 700, 400);
        stage.setScene(scene);
        stage.show();
    }

    // ========== ЗАДАНИЕ 2: Скрытие/показ виджетов ==========
    private static void showWidgetVisibility() {
        Stage stage = new Stage();
        stage.setTitle("Задание 2: Управление видимостью виджетов");
        stage.setResizable(false);

        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #ffffff, #f8f9fa);");

        Label title = new Label("УПРАВЛЕНИЕ ВИДИМОСТЬЮ ВИДЖЕТОВ");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50; -fx-alignment: center;");

        // Создаем виджеты
        TextArea textArea = new TextArea();
        textArea.setPromptText("Это текстовое поле...\nМожете ввести любой текст");
        textArea.setPrefSize(300, 100);
        textArea.setStyle("-fx-font-size: 14px;");

        ProgressBar progressBar = new ProgressBar(0.5);
        progressBar.setPrefWidth(300);
        progressBar.setStyle("-fx-accent: #3498db;");

        Slider slider = new Slider(0, 100, 50);
        slider.setPrefWidth(300);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);

        DatePicker datePicker = new DatePicker();
        datePicker.setPrefWidth(300);

        ColorPicker colorPicker = new ColorPicker(Color.BLUE);
        colorPicker.setPrefWidth(300);

        // Создаем чекбоксы
        CheckBox cb1 = new CheckBox("Показать текстовое поле");
        cb1.setSelected(true);
        cb1.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        CheckBox cb2 = new CheckBox("Показать индикатор выполнения");
        cb2.setSelected(true);
        cb2.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        CheckBox cb3 = new CheckBox("Показать слайдер");
        cb3.setSelected(true);
        cb3.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        CheckBox cb4 = new CheckBox("Показать выбор даты");
        cb4.setSelected(true);
        cb4.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        CheckBox cb5 = new CheckBox("Показать выбор цвета");
        cb5.setSelected(true);
        cb5.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        // Группируем виджеты с их чекбоксами
        VBox widget1 = new VBox(5);
        widget1.getChildren().addAll(cb1, textArea);

        VBox widget2 = new VBox(5);
        widget2.getChildren().addAll(cb2, progressBar);

        VBox widget3 = new VBox(5);
        widget3.getChildren().addAll(cb3, slider);

        VBox widget4 = new VBox(5);
        widget4.getChildren().addAll(cb4, datePicker);

        VBox widget5 = new VBox(5);
        widget5.getChildren().addAll(cb5, colorPicker);

        // Обработчики чекбоксов
        cb1.selectedProperty().addListener((obs, oldVal, newVal) ->
                textArea.setVisible(newVal));

        cb2.selectedProperty().addListener((obs, oldVal, newVal) ->
                progressBar.setVisible(newVal));

        cb3.selectedProperty().addListener((obs, oldVal, newVal) ->
                slider.setVisible(newVal));

        cb4.selectedProperty().addListener((obs, oldVal, newVal) ->
                datePicker.setVisible(newVal));

        cb5.selectedProperty().addListener((obs, oldVal, newVal) ->
                colorPicker.setVisible(newVal));

        // Кнопки управления
        HBox controlButtons = new HBox(10);
        controlButtons.setAlignment(Pos.CENTER);

        Button selectAll = new Button("Выбрать все");
        selectAll.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold;");
        selectAll.setOnAction(e -> {
            cb1.setSelected(true);
            cb2.setSelected(true);
            cb3.setSelected(true);
            cb4.setSelected(true);
            cb5.setSelected(true);
        });

        Button deselectAll = new Button("Снять все");
        deselectAll.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
        deselectAll.setOnAction(e -> {
            cb1.setSelected(false);
            cb2.setSelected(false);
            cb3.setSelected(false);
            cb4.setSelected(false);
            cb5.setSelected(false);
        });

        Button backButton = new Button("Назад в меню");
        backButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");
        backButton.setOnAction(e -> stage.close());

        controlButtons.getChildren().addAll(selectAll, deselectAll, backButton);

        root.getChildren().addAll(title, widget1, widget2, widget3, widget4, widget5, controlButtons);

        Scene scene = new Scene(root, 400, 700);
        stage.setScene(scene);
        stage.show();
    }

    // ========== ЗАДАНИЕ 3: Заказ в ресторане ==========
    private static void showRestaurantOrder() {
        Stage stage = new Stage();
        stage.setTitle("Задание 3: Заказ в ресторане");
        stage.setResizable(false);

        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #fff9e6, #fff2cc);");

        Label title = new Label("МЕНЮ РЕСТОРАНА");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #d35400; -fx-alignment: center;");

        // Данные о блюдах
        class Dish {
            String name;
            double price;
            int quantity;

            Dish(String name, double price) {
                this.name = name;
                this.price = price;
                this.quantity = 0;
            }

            double getTotal() {
                return price * quantity;
            }
        }

        List<Dish> dishes = new ArrayList<>();
        dishes.add(new Dish("Борщ", 250.0));
        dishes.add(new Dish("Стейк", 850.0));
        dishes.add(new Dish("Салат Цезарь", 350.0));
        dishes.add(new Dish("Пицца Маргарита", 450.0));
        dishes.add(new Dish("Паста Карбонара", 380.0));
        dishes.add(new Dish("Рыба на гриле", 620.0));
        dishes.add(new Dish("Десерт Тирамису", 280.0));
        dishes.add(new Dish("Кофе Латте", 180.0));

        VBox dishesBox = new VBox(10);

        // Создаем элементы управления для каждого блюда
        List<CheckBox> checkBoxes = new ArrayList<>();
        List<Spinner<Integer>> spinners = new ArrayList<>();

        for (Dish dish : dishes) {
            HBox dishBox = new HBox(15);
            dishBox.setAlignment(Pos.CENTER_LEFT);
            dishBox.setStyle("-fx-padding: 10; -fx-background-color: white; -fx-background-radius: 5px;");

            CheckBox checkBox = new CheckBox(dish.name);
            checkBox.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
            checkBoxes.add(checkBox);

            Label priceLabel = new Label(String.format("%.2f ₽", dish.price));
            priceLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #27ae60; -fx-font-weight: bold;");

            Spinner<Integer> spinner = new Spinner<>(0, 10, 0);
            spinner.setPrefWidth(80);
            spinner.setEditable(true);
            spinner.setDisable(true);
            spinners.add(spinner);

            // Привязываем спиннер к чекбоксу
            checkBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
                spinner.setDisable(!newVal);
                if (newVal && spinner.getValue() == 0) {
                    spinner.getValueFactory().setValue(1);
                }
            });

            // Связываем спиннер с количеством в объекте Dish
            spinner.valueProperty().addListener((obs, oldVal, newVal) -> {
                dish.quantity = newVal;
                if (newVal == 0 && checkBox.isSelected()) {
                    checkBox.setSelected(false);
                }
            });

            dishBox.getChildren().addAll(checkBox, priceLabel, new Label("Кол-во:"), spinner);
            dishesBox.getChildren().add(dishBox);
        }

        // Кнопки управления
        HBox buttonsBox = new HBox(15);
        buttonsBox.setAlignment(Pos.CENTER);

        Button calculateButton = new Button("Рассчитать заказ");
        calculateButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-pref-width: 200px; -fx-pref-height: 50px; " +
                "-fx-background-color: linear-gradient(to bottom, #2ecc71, #27ae60); " +
                "-fx-text-fill: white; -fx-background-radius: 5px;");

        Button clearButton = new Button("Очистить заказ");
        clearButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-pref-width: 200px; -fx-pref-height: 50px; " +
                "-fx-background-color: linear-gradient(to bottom, #e74c3c, #c0392b); " +
                "-fx-text-fill: white; -fx-background-radius: 5px;");

        Button backButton = new Button("Назад в меню");
        backButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-pref-width: 200px; -fx-pref-height: 50px; " +
                "-fx-background-color: linear-gradient(to bottom, #3498db, #2980b9); " +
                "-fx-text-fill: white; -fx-background-radius: 5px;");

        // Обработчик расчета заказа
        calculateButton.setOnAction(e -> {
            double total = 0;
            StringBuilder receipt = new StringBuilder();
            receipt.append("=== ЧЕК ===\n");
            receipt.append("Ресторан 'Учебный'\n");
            receipt.append("========================\n\n");

            boolean hasOrder = false;
            for (int i = 0; i < dishes.size(); i++) {
                Dish dish = dishes.get(i);
                if (checkBoxes.get(i).isSelected() && dish.quantity > 0) {
                    hasOrder = true;
                    double dishTotal = dish.getTotal();
                    total += dishTotal;
                    receipt.append(String.format("%-20s x%d = %.2f ₽\n",
                            dish.name, dish.quantity, dishTotal));
                }
            }

            if (!hasOrder) {
                showAlert("Ошибка", "Вы не выбрали ни одного блюда!", AlertType.WARNING);
                return;
            }

            receipt.append("\n========================\n");
            receipt.append(String.format("ИТОГО: %.2f ₽\n", total));
            receipt.append("========================\n");
            receipt.append("Спасибо за заказ!");

            // Показываем чек в модальном окне
            showReceipt(receipt.toString(), total);
        });

        // Обработчик очистки
        clearButton.setOnAction(e -> {
            for (int i = 0; i < dishes.size(); i++) {
                checkBoxes.get(i).setSelected(false);
                spinners.get(i).getValueFactory().setValue(0);
                dishes.get(i).quantity = 0;
            }
        });

        backButton.setOnAction(e -> stage.close());

        buttonsBox.getChildren().addAll(calculateButton, clearButton, backButton);

        // Создаем скроллируемую область для блюд
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(dishesBox);
        scrollPane.setPrefHeight(350);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        root.getChildren().addAll(title, scrollPane, buttonsBox);

        Scene scene = new Scene(root, 600, 700);
        stage.setScene(scene);
        stage.show();
    }

    private static void showReceipt(String receiptText, double total) {
        Stage receiptStage = new Stage();
        receiptStage.setTitle("Чек заказа");
        receiptStage.setResizable(false);

        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: white;");

        Label title = new Label("ВАШ ЧЕК");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50; -fx-alignment: center;");

        TextArea receiptArea = new TextArea(receiptText);
        receiptArea.setEditable(false);
        receiptArea.setPrefSize(400, 300);
        receiptArea.setStyle("-fx-font-size: 14px; -fx-font-family: 'Courier New'; -fx-background-color: #f8f9fa;");

        Label totalLabel = new Label(String.format("Общая сумма: %.2f ₽", total));
        totalLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #27ae60; -fx-alignment: center;");

        Button okButton = new Button("OK");
        okButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-pref-width: 150px; -fx-pref-height: 40px; " +
                "-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-background-radius: 5px;");
        okButton.setOnAction(e -> receiptStage.close());

        root.getChildren().addAll(title, receiptArea, totalLabel, okButton);

        Scene scene = new Scene(root, 500, 500);
        receiptStage.setScene(scene);
        receiptStage.showAndWait();
    }

    // ========== ЗАДАНИЕ 4: Калькулятор ==========
    private static void showCalculator() {
        Stage stage = new Stage();
        stage.setTitle("Задание 4: Калькулятор");
        stage.setResizable(false);

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #2c3e50, #34495e);");

        // Дисплей калькулятора
        TextField display = new TextField("0");
        display.setEditable(false);
        display.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: white; " +
                "-fx-background-color: #1a252f; -fx-alignment: center-right; " +
                "-fx-pref-height: 80px; -fx-background-radius: 10px; -fx-border-radius: 10px;");

        // Сетка для кнопок
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 0, 0, 0));

        // Кнопки калькулятора
        String[][] buttons = {
                {"C", "±", "%", "÷"},
                {"7", "8", "9", "×"},
                {"4", "5", "6", "−"},
                {"1", "2", "3", "+"},
                {"0", ".", "="}
        };

        // Стили для разных типов кнопок
        Map<String, String> buttonStyles = new HashMap<>();
        buttonStyles.put("number", "-fx-font-size: 20px; -fx-font-weight: bold; -fx-pref-width: 70px; -fx-pref-height: 70px; " +
                "-fx-background-color: #34495e; -fx-text-fill: white; -fx-background-radius: 35px;");
        buttonStyles.put("operator", "-fx-font-size: 20px; -fx-font-weight: bold; -fx-pref-width: 70px; -fx-pref-height: 70px; " +
                "-fx-background-color: #3498db; -fx-text-fill: white; -fx-background-radius: 35px;");
        buttonStyles.put("equals", "-fx-font-size: 20px; -fx-font-weight: bold; -fx-pref-width: 150px; -fx-pref-height: 70px; " +
                "-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-background-radius: 35px;");
        buttonStyles.put("clear", "-fx-font-size: 20px; -fx-font-weight: bold; -fx-pref-width: 70px; -fx-pref-height: 70px; " +
                "-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-background-radius: 35px;");

        // Переменные для хранения состояния калькулятора
        final double[] firstNumber = {0};
        final double[] secondNumber = {0};
        final String[] currentOperator = {""};
        final boolean[] startNewNumber = {true};

        // Создаем кнопки
        for (int row = 0; row < buttons.length; row++) {
            for (int col = 0; col < buttons[row].length; col++) {
                String text = buttons[row][col];
                Button button = new Button(text);

                // Определяем стиль кнопки
                if (text.matches("[0-9]")) {
                    button.setStyle(buttonStyles.get("number"));
                } else if (text.equals("=")) {
                    button.setStyle(buttonStyles.get("equals"));
                    GridPane.setColumnSpan(button, 2);
                } else if (text.equals("C")) {
                    button.setStyle(buttonStyles.get("clear"));
                } else if (text.equals("±") || text.equals("%") || text.equals(".")) {
                    button.setStyle(buttonStyles.get("number"));
                } else {
                    button.setStyle(buttonStyles.get("operator"));
                }

                // Обработчики для кнопок
                button.setOnAction(e -> {
                    switch (text) {
                        case "C":
                            display.setText("0");
                            firstNumber[0] = 0;
                            secondNumber[0] = 0;
                            currentOperator[0] = "";
                            startNewNumber[0] = true;
                            break;

                        case "±":
                            if (!display.getText().equals("0")) {
                                double value = Double.parseDouble(display.getText());
                                display.setText(String.valueOf(-value));
                            }
                            break;

                        case "%":
                            if (!display.getText().equals("0")) {
                                double value = Double.parseDouble(display.getText());
                                display.setText(String.valueOf(value / 100));
                            }
                            break;

                        case "+":
                        case "−":
                        case "×":
                        case "÷":
                            if (!currentOperator[0].isEmpty()) {
                                // Выполняем предыдущую операцию
                                calculate(firstNumber[0], Double.parseDouble(display.getText()),
                                        currentOperator[0], display);
                            }
                            firstNumber[0] = Double.parseDouble(display.getText());
                            currentOperator[0] = text;
                            startNewNumber[0] = true;
                            break;

                        case "=":
                            if (!currentOperator[0].isEmpty()) {
                                secondNumber[0] = Double.parseDouble(display.getText());
                                calculate(firstNumber[0], secondNumber[0], currentOperator[0], display);
                                currentOperator[0] = "";
                                startNewNumber[0] = true;
                            }
                            break;

                        case ".":
                            if (!display.getText().contains(".")) {
                                display.setText(display.getText() + ".");
                            }
                            break;

                        default: // Цифры
                            if (startNewNumber[0] || display.getText().equals("0")) {
                                display.setText(text);
                                startNewNumber[0] = false;
                            } else {
                                display.setText(display.getText() + text);
                            }
                            break;
                    }
                });

                // Добавляем эффект при наведении
                button.setOnMouseEntered(event ->
                        button.setStyle(button.getStyle() + "-fx-effect: dropshadow(gaussian, rgba(255,255,255,0.3), 10, 0.5, 0, 0);"));

                button.setOnMouseExited(event -> {
                    if (text.matches("[0-9]")) {
                        button.setStyle(buttonStyles.get("number"));
                    } else if (text.equals("=")) {
                        button.setStyle(buttonStyles.get("equals"));
                    } else if (text.equals("C")) {
                        button.setStyle(buttonStyles.get("clear"));
                    } else if (text.equals("±") || text.equals("%") || text.equals(".")) {
                        button.setStyle(buttonStyles.get("number"));
                    } else {
                        button.setStyle(buttonStyles.get("operator"));
                    }
                });

                // Для кнопки "0" устанавливаем двойную ширину
                if (text.equals("0")) {
                    GridPane.setColumnSpan(button, 2);
                    grid.add(button, col, row);
                } else {
                    grid.add(button, col, row);
                }
            }
        }

        // Кнопка возврата
        HBox bottomBox = new HBox(10);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(20, 0, 0, 0));

        Button backButton = new Button("Назад в меню");
        backButton.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-pref-width: 200px; -fx-pref-height: 40px; " +
                "-fx-background-color: #3498db; -fx-text-fill: white; -fx-background-radius: 5px;");
        backButton.setOnAction(e -> stage.close());

        bottomBox.getChildren().add(backButton);

        root.getChildren().addAll(display, grid, bottomBox);

        Scene scene = new Scene(root, 350, 550);
        stage.setScene(scene);
        stage.show();
    }

    private static void calculate(double first, double second, String operator, TextField display) {
        double result = 0;
        boolean error = false;

        switch (operator) {
            case "+":
                result = first + second;
                break;
            case "−":
                result = first - second;
                break;
            case "×":
                result = first * second;
                break;
            case "÷":
                if (second == 0) {
                    display.setText("Ошибка: деление на 0");
                    error = true;
                } else {
                    result = first / second;
                }
                break;
        }

        if (!error) {
            // Округляем до 10 знаков после запятой
            result = Math.round(result * 10000000000.0) / 10000000000.0;
            display.setText(String.valueOf(result));
        }
    }

    // ========== ЗАДАНИЕ 5: Текстовый флаг ==========
    private static void showTextFlag() {
        Stage stage = new Stage();
        stage.setTitle("Задание 5: Текстовый флаг");
        stage.setResizable(false);

        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #ffffff, #f5f5f5);");

        Label title = new Label("ТЕКСТОВЫЙ ФЛАГ");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Цвета для полос флага
        Map<String, Color> colors = new LinkedHashMap<>();
        colors.put("Красный", Color.RED);
        colors.put("Синий", Color.BLUE);
        colors.put("Зеленый", Color.GREEN);
        colors.put("Желтый", Color.YELLOW);
        colors.put("Белый", Color.WHITE);
        colors.put("Черный", Color.BLACK);

        // Радио-кнопки для каждой полосы
        VBox stripe1Box = new VBox(10);
        Label stripe1Label = new Label("Верхняя полоса:");
        stripe1Label.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        ToggleGroup group1 = new ToggleGroup();
        VBox radioBox1 = new VBox(5);
        for (String colorName : colors.keySet()) {
            RadioButton rb = new RadioButton(colorName);
            rb.setToggleGroup(group1);
            rb.setStyle("-fx-font-size: 12px;");
            radioBox1.getChildren().add(rb);
        }
        ((RadioButton) radioBox1.getChildren().get(0)).setSelected(true);

        stripe1Box.getChildren().addAll(stripe1Label, radioBox1);

        VBox stripe2Box = new VBox(10);
        Label stripe2Label = new Label("Средняя полоса:");
        stripe2Label.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        ToggleGroup group2 = new ToggleGroup();
        VBox radioBox2 = new VBox(5);
        for (String colorName : colors.keySet()) {
            RadioButton rb = new RadioButton(colorName);
            rb.setToggleGroup(group2);
            rb.setStyle("-fx-font-size: 12px;");
            radioBox2.getChildren().add(rb);
        }
        ((RadioButton) radioBox2.getChildren().get(1)).setSelected(true);

        stripe2Box.getChildren().addAll(stripe2Label, radioBox2);

        VBox stripe3Box = new VBox(10);
        Label stripe3Label = new Label("Нижняя полоса:");
        stripe3Label.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        ToggleGroup group3 = new ToggleGroup();
        VBox radioBox3 = new VBox(5);
        for (String colorName : colors.keySet()) {
            RadioButton rb = new RadioButton(colorName);
            rb.setToggleGroup(group3);
            rb.setStyle("-fx-font-size: 12px;");
            radioBox3.getChildren().add(rb);
        }
        ((RadioButton) radioBox3.getChildren().get(2)).setSelected(true);

        stripe3Box.getChildren().addAll(stripe3Label, radioBox3);

        // Визуальное представление флага
        Canvas flagCanvas = new Canvas(300, 200);
        GraphicsContext gc = flagCanvas.getGraphicsContext2D();

        // Начальная отрисовка флага
        updateFlag(gc, colors, group1, group2, group3);

        // Кнопка "Нарисовать"
        Button drawButton = new Button("НАРИСОВАТЬ ФЛАГ");
        drawButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-pref-width: 250px; -fx-pref-height: 50px; " +
                "-fx-background-color: linear-gradient(to bottom, #e74c3c, #c0392b); " +
                "-fx-text-fill: white; -fx-background-radius: 5px;");

        // Текстовое отображение выбранных цветов
        Label resultLabel = new Label();
        resultLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50; " +
                "-fx-background-color: #ecf0f1; -fx-padding: 10px; -fx-background-radius: 5px;");
        resultLabel.setPrefWidth(300);
        resultLabel.setAlignment(Pos.CENTER);

        // Обработчик кнопки
        drawButton.setOnAction(e -> {
            updateFlag(gc, colors, group1, group2, group3);

            // Получаем выбранные цвета
            String color1 = getSelectedColor(group1);
            String color2 = getSelectedColor(group2);
            String color3 = getSelectedColor(group3);

            resultLabel.setText(String.format("%s, %s, %s", color1, color2, color3));
        });

        // Кнопка возврата
        Button backButton = new Button("Назад в меню");
        backButton.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-pref-width: 200px; -fx-pref-height: 40px; " +
                "-fx-background-color: #3498db; -fx-text-fill: white; -fx-background-radius: 5px;");
        backButton.setOnAction(e -> stage.close());

        HBox stripesBox = new HBox(20);
        stripesBox.setAlignment(Pos.CENTER);
        stripesBox.getChildren().addAll(stripe1Box, stripe2Box, stripe3Box);

        VBox flagBox = new VBox(10);
        flagBox.setAlignment(Pos.CENTER);
        flagBox.getChildren().addAll(flagCanvas, resultLabel);

        root.getChildren().addAll(title, stripesBox, flagBox, drawButton, backButton);

        Scene scene = new Scene(root, 700, 700);
        stage.setScene(scene);
        stage.show();
    }

    private static void updateFlag(GraphicsContext gc, Map<String, Color> colors,
                                   ToggleGroup g1, ToggleGroup g2, ToggleGroup g3) {
        // Очищаем canvas
        gc.clearRect(0, 0, 300, 200);

        // Рисуем рамку
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeRect(0, 0, 300, 200);

        // Рисуем полосы флага
        String color1 = getSelectedColor(g1);
        String color2 = getSelectedColor(g2);
        String color3 = getSelectedColor(g3);

        gc.setFill(colors.get(color1));
        gc.fillRect(5, 5, 290, 60); // Верхняя полоса

        gc.setFill(colors.get(color2));
        gc.fillRect(5, 70, 290, 60); // Средняя полоса

        gc.setFill(colors.get(color3));
        gc.fillRect(5, 135, 290, 60); // Нижняя полоса

        // Добавляем текст с названиями цветов
        gc.setFill(Color.BLACK);
        gc.setFont(new Font("Arial", 12));
        gc.fillText(color1, 10, 40);
        gc.fillText(color2, 10, 105);
        gc.fillText(color3, 10, 170);
    }

    private static String getSelectedColor(ToggleGroup group) {
        RadioButton selected = (RadioButton) group.getSelectedToggle();
        return selected != null ? selected.getText() : "Не выбран";
    }

    // ========== ЗАДАНИЕ 6: Рисование геометрических фигур ==========
    private static void showGeometryDrawing() {
        Stage stage = new Stage();
        stage.setTitle("Задание 6: Рисование геометрических фигур");

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #ecf0f1;");

        // Canvas для рисования
        Canvas canvas = new Canvas(800, 500);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Очищаем canvas
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Панель кнопок
        VBox buttonPanel = new VBox(10);
        buttonPanel.setPadding(new Insets(20));
        buttonPanel.setStyle("-fx-background-color: #2c3e50;");

        Label title = new Label("РИСОВАНИЕ ФИГУР");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");

        // Кнопки для рисования фигур
        Button circleBtn = createGeometryButton("Круг");
        Button rectangleBtn = createGeometryButton("Прямоугольник");
        Button triangleBtn = createGeometryButton("Треугольник");
        Button clearBtn = createGeometryButton("Очистить", "#e74c3c");

        // Обработчики кнопок
        Random random = new Random();

        circleBtn.setOnAction(e -> {
            double x = random.nextDouble() * (canvas.getWidth() - 100) + 50;
            double y = random.nextDouble() * (canvas.getHeight() - 100) + 50;
            double radius = random.nextDouble() * 50 + 20;

            gc.setFill(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256), 0.7));
            gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);

            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);
            gc.strokeOval(x - radius, y - radius, radius * 2, radius * 2);
        });

        rectangleBtn.setOnAction(e -> {
            double x = random.nextDouble() * (canvas.getWidth() - 150) + 50;
            double y = random.nextDouble() * (canvas.getHeight() - 150) + 50;
            double width = random.nextDouble() * 100 + 50;
            double height = random.nextDouble() * 100 + 50;

            gc.setFill(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256), 0.7));
            gc.fillRect(x, y, width, height);

            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);
            gc.strokeRect(x, y, width, height);
        });

        triangleBtn.setOnAction(e -> {
            double x = random.nextDouble() * (canvas.getWidth() - 150) + 50;
            double y = random.nextDouble() * (canvas.getHeight() - 150) + 50;
            double size = random.nextDouble() * 80 + 40;

            double[] xPoints = {x, x + size, x + size / 2};
            double[] yPoints = {y + size, y + size, y};

            gc.setFill(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256), 0.7));
            gc.fillPolygon(xPoints, yPoints, 3);

            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);
            gc.strokePolygon(xPoints, yPoints, 3);
        });

        clearBtn.setOnAction(e -> {
            gc.setFill(Color.WHITE);
            gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        });

        // Кнопка возврата
        Button backButton = createGeometryButton("Назад в меню", "#3498db");
        backButton.setOnAction(e -> stage.close());

        buttonPanel.getChildren().addAll(title, circleBtn, rectangleBtn, triangleBtn, clearBtn, backButton);

        root.setLeft(buttonPanel);
        root.setCenter(canvas);

        Scene scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
        stage.show();
    }

    private static Button createGeometryButton(String text) {
        return createGeometryButton(text, "#2ecc71");
    }

    private static Button createGeometryButton(String text, String color) {
        Button btn = new Button(text);
        btn.setPrefWidth(150);
        btn.setPrefHeight(40);
        btn.setStyle(String.format(
                "-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: white; " +
                        "-fx-background-color: %s; -fx-background-radius: 5px; " +
                        "-fx-cursor: hand;",
                color
        ));

        btn.setOnMouseEntered(e ->
                btn.setStyle(String.format(
                        "-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: white; " +
                                "-fx-background-color: %s; -fx-background-radius: 5px; " +
                                "-fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0.5, 0, 2);",
                        color
                ))
        );

        btn.setOnMouseExited(e ->
                btn.setStyle(String.format(
                        "-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: white; " +
                                "-fx-background-color: %s; -fx-background-radius: 5px; " +
                                "-fx-cursor: hand;",
                        color
                ))
        );

        return btn;
    }

    // ========== ЗАДАНИЕ 7: Drag & Drop геометрических фигур ==========
    private static void showGeometryDragDrop() {
        Stage stage = new Stage();
        stage.setTitle("Задание 7: Drag & Drop геометрических фигур");

        Pane root = new Pane();
        root.setStyle("-fx-background-color: #f8f9fa;");
        root.setPrefSize(800, 600);

        // Создаем несколько фигур
        List<javafx.scene.shape.Shape> shapes = new ArrayList<>();
        Random random = new Random();

        // Создаем круги
        for (int i = 0; i < 3; i++) {
            javafx.scene.shape.Circle circle = new javafx.scene.shape.Circle(
                    random.nextDouble() * 700 + 50,
                    random.nextDouble() * 500 + 50,
                    random.nextDouble() * 50 + 30
            );
            circle.setFill(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256), 0.7));
            circle.setStroke(Color.BLACK);
            circle.setStrokeWidth(2);

            shapes.add(circle);
            root.getChildren().add(circle);
        }

        // Создаем прямоугольники
        for (int i = 0; i < 3; i++) {
            javafx.scene.shape.Rectangle rect = new javafx.scene.shape.Rectangle(
                    random.nextDouble() * 700 + 50,
                    random.nextDouble() * 500 + 50,
                    random.nextDouble() * 100 + 50,
                    random.nextDouble() * 100 + 50
            );
            rect.setFill(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256), 0.7));
            rect.setStroke(Color.BLACK);
            rect.setStrokeWidth(2);

            shapes.add(rect);
            root.getChildren().add(rect);
        }

        // Создаем треугольники
        for (int i = 0; i < 2; i++) {
            javafx.scene.shape.Polygon triangle = new javafx.scene.shape.Polygon();
            double x = random.nextDouble() * 700 + 50;
            double y = random.nextDouble() * 500 + 50;
            double size = random.nextDouble() * 80 + 40;

            triangle.getPoints().addAll(
                    x, y + size,
                    x + size, y + size,
                    x + size / 2, y
            );

            triangle.setFill(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256), 0.7));
            triangle.setStroke(Color.BLACK);
            triangle.setStrokeWidth(2);

            shapes.add(triangle);
            root.getChildren().add(triangle);
        }

        // Обработчики событий для каждой фигуры
        for (javafx.scene.shape.Shape shape : shapes) {
            final double[] offsetX = new double[1];
            final double[] offsetY = new double[1];

            // Обработчик перетаскивания (левая кнопка мыши)
            shape.setOnMousePressed(event -> {
                if (event.isPrimaryButtonDown()) {
                    offsetX[0] = shape.getLayoutX() - event.getSceneX();
                    offsetY[0] = shape.getLayoutY() - event.getSceneY();

                    // Поднимаем фигуру на передний план
                    shape.toFront();
                }
            });

            shape.setOnMouseDragged(event -> {
                if (event.isPrimaryButtonDown()) {
                    shape.setLayoutX(event.getSceneX() + offsetX[0]);
                    shape.setLayoutY(event.getSceneY() + offsetY[0]);
                }
            });

            // Обработчик смены цвета (правая кнопка мыши)
            shape.setOnMouseClicked(event -> {
                if (event.getButton().name().equals("SECONDARY")) {
                    shape.setFill(Color.rgb(
                            random.nextInt(256),
                            random.nextInt(256),
                            random.nextInt(256),
                            0.7
                    ));
                }
            });

            // Эффект при наведении
            shape.setOnMouseEntered(event -> {
                shape.setStrokeWidth(3);
                shape.setStroke(Color.DARKBLUE);
            });

            shape.setOnMouseExited(event -> {
                shape.setStrokeWidth(2);
                shape.setStroke(Color.BLACK);
            });
        }

        // Панель управления
        VBox controlPanel = new VBox(10);
        controlPanel.setPadding(new Insets(20));
        controlPanel.setStyle("-fx-background-color: rgba(44, 62, 80, 0.9); -fx-background-radius: 10px;");
        controlPanel.setLayoutX(20);
        controlPanel.setLayoutY(20);

        Label title = new Label("DRAG & DROP ФИГУР");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label instructions = new Label("Инструкции:");
        instructions.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #3498db;");

        Label instr1 = new Label("• ЛКМ: перетащить фигуру");
        instr1.setStyle("-fx-font-size: 12px; -fx-text-fill: white;");

        Label instr2 = new Label("• ПКМ: изменить цвет");
        instr2.setStyle("-fx-font-size: 12px; -fx-text-fill: white;");

        Label instr3 = new Label("• Наведите курсор на фигуру");
        instr3.setStyle("-fx-font-size: 12px; -fx-text-fill: white;");

        // Кнопка добавления новой фигуры
        Button addCircleBtn = new Button("Добавить круг");
        addCircleBtn.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold;");
        addCircleBtn.setOnAction(e -> {
            javafx.scene.shape.Circle circle = new javafx.scene.shape.Circle(
                    random.nextDouble() * 700 + 50,
                    random.nextDouble() * 500 + 50,
                    random.nextDouble() * 50 + 30
            );
            circle.setFill(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256), 0.7));
            circle.setStroke(Color.BLACK);
            circle.setStrokeWidth(2);

            // Добавляем обработчики событий
            final double[] offsetX = new double[1];
            final double[] offsetY = new double[1];

            circle.setOnMousePressed(event -> {
                if (event.isPrimaryButtonDown()) {
                    offsetX[0] = circle.getLayoutX() - event.getSceneX();
                    offsetY[0] = circle.getLayoutY() - event.getSceneY();
                    circle.toFront();
                }
            });

            circle.setOnMouseDragged(event -> {
                if (event.isPrimaryButtonDown()) {
                    circle.setLayoutX(event.getSceneX() + offsetX[0]);
                    circle.setLayoutY(event.getSceneY() + offsetY[0]);
                }
            });

            circle.setOnMouseClicked(event -> {
                if (event.getButton().name().equals("SECONDARY")) {
                    circle.setFill(Color.rgb(
                            random.nextInt(256),
                            random.nextInt(256),
                            random.nextInt(256),
                            0.7
                    ));
                }
            });

            circle.setOnMouseEntered(event -> {
                circle.setStrokeWidth(3);
                circle.setStroke(Color.DARKBLUE);
            });

            circle.setOnMouseExited(event -> {
                circle.setStrokeWidth(2);
                circle.setStroke(Color.BLACK);
            });

            shapes.add(circle);
            root.getChildren().add(circle);
        });

        // Кнопка возврата
        Button backButton = new Button("Назад в меню");
        backButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");
        backButton.setOnAction(e -> stage.close());

        controlPanel.getChildren().addAll(title, instructions, instr1, instr2, instr3,
                addCircleBtn, backButton);

        root.getChildren().add(controlPanel);

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    // ========== ОСТАЛЬНЫЕ ДЕМОНСТРАЦИИ (консольные задания) ==========

    private static void showMyButtonDemo() {
        showConsoleTask("Демонстрация MyButton",
                "Запустите программу в консольном режиме для демонстрации MyButton");
    }

    private static void showBalanceDemo() {
        showConsoleTask("Демонстрация Balance",
                "Запустите программу в консольном режиме для демонстрации весов");
    }

    private static void showBellDemo() {
        showConsoleTask("Демонстрация Bell",
                "Запустите программу в консольном режиме для демонстрации колокола");
    }

    private static void showOddEvenSeparatorDemo() {
        showConsoleTask("Демонстрация разделителя четных/нечетных",
                "Запустите программу в консольном режиме для демонстрации");
    }

    private static void showMyTableDemo() {
        showConsoleTask("Демонстрация MyTable",
                "Запустите программу в консольном режиме для демонстрации таблицы");
    }

    private static void showGeometryConsole() {
        showConsoleTask("Демонстрация геометрических фигур",
                "Запустите программу в консольном режиме для демонстрации геометрических фигур");
    }

    private static void showCollectionsDemo() {
        showConsoleTask("Демонстрация Collections API",
                "Запустите программу в консольном режиме для демонстрации работы с коллекциями");
    }

    private static void showPrimesGeneratorDemo() {
        showConsoleTask("Демонстрация генератора простых чисел",
                "Запустите программу в консольном режиме для демонстрации генератора простых чисел");
    }

    private static void showHumanCollectionsDemo() {
        showConsoleTask("Демонстрация класса Human и коллекций",
                "Запустите программу в консольном режиме для демонстрации работы с классом Human");
    }

    private static void showWordFrequencyDemo() {
        showConsoleTask("Демонстрация частоты слов",
                "Запустите программу в консольном режиме для демонстрации подсчета частоты слов");
    }

    private static void showMapInversionDemo() {
        showConsoleTask("Демонстрация инверсии Map",
                "Запустите программу в консольном режиме для демонстрации инверсии Map");
    }

    private static void showJsonStreamDemo() {
        showConsoleTask("Демонстрация потоков данных и JSON",
                "Запустите программу в консольном режиме для демонстрации работы с потоками и JSON");
    }

    private static void showConsoleTask(String title, String message) {
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setResizable(false);

        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #ffffff, #f8f9fa);");

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label messageLabel = new Label(message);
        messageLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #7f8c8d; -fx-alignment: center;");
        messageLabel.setWrapText(true);

        Label instructionLabel = new Label("Чтобы запустить эту демонстрацию:\n" +
                "1. Закройте это окно\n" +
                "2. В главном меню выберите 'Консольный режим'\n" +
                "3. Выберите соответствующую опцию в меню");
        instructionLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #e74c3c; -fx-font-weight: bold; -fx-alignment: center;");
        instructionLabel.setWrapText(true);

        Button okButton = new Button("Понятно");
        okButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-pref-width: 150px; -fx-pref-height: 40px; " +
                "-fx-background-color: #3498db; -fx-text-fill: white; -fx-background-radius: 5px;");
        okButton.setOnAction(e -> stage.close());

        root.getChildren().addAll(titleLabel, messageLabel, instructionLabel, okButton);

        Scene scene = new Scene(root, 500, 300);
        stage.setScene(scene);
        stage.show();
    }

    // ========== ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ ==========

    private static void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Кастомизация диалогового окна
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-background-color: white;");
        dialogPane.getStyleClass().add("custom-alert");

        alert.showAndWait();
    }

    // ========== КОНСОЛЬНОЕ ПРИЛОЖЕНИЕ ==========

    private static void runConsoleApplication() {
        scanner = new Scanner(System.in, "UTF-8");
        while (true) {
            System.out.println("\n" + "═".repeat(60));
            System.out.println("ГЛАВНОЕ МЕНЮ - КОНСОЛЬНОЕ ПРИЛОЖЕНИЕ");

            System.out.println("\n Задачи (1-6):");
            System.out.println("   1. Кнопка (MyButton)");
            System.out.println("   2. Баланс (Balance)");
            System.out.println("   3. Колокол (Bell)");
            System.out.println("   4. Разделитель четных/нечетных");
            System.out.println("   5. Таблица (MyTable)");
            System.out.println("   6. Геометрические фигуры");

            System.out.println("\n Коллекции (7-11):");
            System.out.println("   7. Работа с Collections API");
            System.out.println("   8. Генератор простых чисел");
            System.out.println("   9. Класс Human и коллекции");
            System.out.println("  10. Частота слов в тексте");
            System.out.println("  11. Инверсия Map<K, V>");

            System.out.println("\n STREAM API & JSON:");
            System.out.println("  12. Демонстрация потоков данных");

            System.out.println("\n GUI режим:");
            System.out.println("  13. Переключиться в GUI режим");

            System.out.println("\n Выход:");
            System.out.println("   0. Выход из программы");

            System.out.print("\n Введите номер (0-13): ");

            if (!scanner.hasNextInt()) {
                System.out.println("Ошибка: введите число!");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); // Очистка буфера

            switch (choice) {
                case 1:
                    demoMyButton();
                    break;
                case 2:
                    demoBalance();
                    break;
                case 3:
                    demoBell();
                    break;
                case 4:
                    demoOddEvenSeparator();
                    break;
                case 5:
                    demoMyTable();
                    break;
                case 6:
                    demoGeometry();
                    break;
                case 7:
                    CollectionsTasks.demo();
                    break;
                case 8:
                    PrimesGeneratorTest.demo();
                    break;
                case 9:
                    demoHumanCollections();
                    break;
                case 10:
                    demoWordFrequency();
                    break;
                case 11:
                    demoMapInversion();
                    break;
                case 12:
                    JsonStreamDemo.demo();
                    break;
                case 13:
                    System.out.println("\nПереход в GUI режим...");
                    System.out.println("Перезапустите программу с параметром: --gui");
                    scanner.close();
                    return;
                case 0:
                    System.out.println("\nПрограмма завершена. До свидания!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Неверный выбор (0-13)");
            }

            if (choice != 0 && choice != 13) {
                System.out.print("\nНажмите Enter для продолжения...");
                scanner.nextLine();
            }
        }
    }

    private static void demoMyButton() {
        System.out.println("\n" + "═".repeat(60));
        System.out.println("ДЕМОНСТРАЦИЯ 1: КНОПКА (MyButton)");
        System.out.println("═".repeat(60));

        MyButton button = new MyButton();

        while (true) {
            System.out.print("Нажмите Enter для клика (q - выход): ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("q")) {
                System.out.println("\nВсего кликов: " + button.getClickCount());
                break;
            }

            button.click();
            System.out.println("Клик! Всего кликов: " + button.getClickCount());
        }
    }

    private static void demoBalance() {
        System.out.println("\n" + "═".repeat(60));
        System.out.println("ЗАДАНИЕ 2: ВЕСЫ");
        System.out.println("═".repeat(60));

        Balance balance = new Balance();

        System.out.println("Команды: l <вес> - левая, r <вес> - правая, result - результат, q - выход");

        while (true) {
            System.out.print("\nКоманда: ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("q")) break;
            else if (input.equalsIgnoreCase("result")) {
                System.out.print("Результат: ");
                balance.result();
            }
        }
    }

    private static void demoBell() {
        System.out.println("\n" + "═".repeat(60));
        System.out.println("ДЕМОНСТРАЦИЯ 3: КОЛОКОЛ");
        System.out.println("═".repeat(60));
        System.out.println("Колокол издает звуки 'ding' и 'dong' поочередно.");
        System.out.println("Нажимайте Enter для звука колокола, 'q' для выхода");

        Bell bell = new Bell();
        int soundCount = 0;

        while (true) {
            System.out.print("\nНажмите Enter для звука (q - выход): ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("q")) {
                System.out.println("\nВсего звуков: " + soundCount);
                break;
            }

            soundCount++;
            System.out.print("  Звук " + soundCount + ": ");
            bell.sound();
        }
    }

    private static void demoOddEvenSeparator() {
        System.out.println("\n" + "═".repeat(60));
        System.out.println("ДЕМОНСТРАЦИЯ 4: РАЗДЕЛИТЕЛЬ ЧЕТНЫХ/НЕЧЕТНЫХ");
        System.out.println("═".repeat(60));

        OddEvenSeparator separator = new OddEvenSeparator();

        System.out.println("Команды: add <число> - добавить, even - четные, odd - нечетные");
        System.out.println("all - все числа, clear - очистить, stats - статистика, q - выход");

        while (true) {
            System.out.print("\nКоманда: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("q")) {
                System.out.println("\nИтоговая статистика:");
                System.out.println("  Всего чисел: " + separator.getNumbers().size());
                System.out.println("  Четных: " + separator.getEvenNumbers().size());
                System.out.println("  Нечетных: " + separator.getOddNumbers().size());
                break;
            } else if (input.equalsIgnoreCase("even")) {
                System.out.print("  Четные числа: ");
                separator.even();
            } else if (input.equalsIgnoreCase("odd")) {
                System.out.print("  Нечетные числа: ");
                separator.odd();
            } else if (input.equalsIgnoreCase("all")) {
                System.out.println("  Все числа: " + separator.getNumbers());
            } else if (input.equalsIgnoreCase("clear")) {
                separator = new OddEvenSeparator();
                System.out.println("  Разделитель очищен");
            } else if (input.equalsIgnoreCase("stats")) {
                System.out.println("  Статистика:");
                System.out.println("    Всего чисел: " + separator.getNumbers().size());
                System.out.println("    Четных: " + separator.getEvenNumbers().size());
                System.out.println("    Нечетных: " + separator.getOddNumbers().size());
            } else if (input.startsWith("add ")) {
                try {
                    int number = Integer.parseInt(input.substring(4).trim());
                    separator.addNumber(number);
                    System.out.println("  Добавлено: " + number);
                } catch (NumberFormatException e) {
                    System.out.println("  Ошибка: после 'add' должно быть число");
                }
            } else {
                System.out.println("  Неизвестная команда");
            }
        }
    }

    private static void demoMyTable() {
        System.out.println("\n" + "═".repeat(60));
        System.out.println("ДЕМОНСТРАЦИЯ 5: ТАБЛИЦА (MyTable)");
        System.out.println("═".repeat(60));

        System.out.print("Введите количество строк: ");
        int rows = scanner.nextInt();
        System.out.print("Введите количество столбцов: ");
        int cols = scanner.nextInt();
        scanner.nextLine();

        MyTable table = new MyTable(rows, cols);

        System.out.println("\nСоздана таблица " + rows + "x" + cols);
        System.out.println("\nКоманды:");
        System.out.println("  set <строка> <столбец> <значение> - установить значение");
        System.out.println("  get <строка> <столбец>           - получить значение");
        System.out.println("  show                             - показать таблицу");
        System.out.println("  avg                              - среднее значение");
        System.out.println("  info                             - информация");
        System.out.println("  random                           - заполнить случайно");
        System.out.println("  clear                            - очистить");
        System.out.println("  q                                - выход");

        while (true) {
            System.out.print("\nКоманда: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("q")) {
                System.out.println("\nИтоговая таблица:");
                System.out.println(table);
                System.out.println("Среднее значение: " + table.average());
                break;
            }

            try {
                if (input.equalsIgnoreCase("show")) {
                    System.out.println("\nТаблица:");
                    System.out.println(table);
                } else if (input.equalsIgnoreCase("avg")) {
                    System.out.println("  Среднее: " + table.average());
                } else if (input.equalsIgnoreCase("info")) {
                    System.out.println("  Информация:");
                    System.out.println("    Строки: " + table.rows());
                    System.out.println("    Столбцы: " + table.cols());
                } else if (input.equalsIgnoreCase("random")) {
                    Random rand = new Random();
                    for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < cols; j++) {
                            table.setValue(i, j, rand.nextInt(100));
                        }
                    }
                    System.out.println("  Таблица заполнена случайными числами");
                } else if (input.equalsIgnoreCase("clear")) {
                    for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < cols; j++) {
                            table.setValue(i, j, 0);
                        }
                    }
                    System.out.println("  Таблица очищена");
                } else if (input.startsWith("set ")) {
                    String[] parts = input.split(" ");
                    int row = Integer.parseInt(parts[1]);
                    int col = Integer.parseInt(parts[2]);
                    int value = Integer.parseInt(parts[3]);
                    table.setValue(row, col, value);
                    System.out.println("  Установлено " + value + " в [" + row + "," + col + "]");
                } else if (input.startsWith("get ")) {
                    String[] parts = input.split(" ");
                    int row = Integer.parseInt(parts[1]);
                    int col = Integer.parseInt(parts[2]);
                    int value = table.getValue(row, col);
                    System.out.println("  Значение в [" + row + "," + col + "]: " + value);
                } else {
                    System.out.println("  Неизвестная команда");
                }
            } catch (Exception e) {
                System.out.println("  Ошибка: " + e.getMessage());
            }
        }
    }

    private static void demoGeometry() {
        System.out.println("\n" + "═".repeat(60));
        System.out.println("ДЕМОНСТРАЦИЯ 6: ГЕОМЕТРИЧЕСКИЕ ФИГУРЫ");
        System.out.println("═".repeat(60));

        while (true) {
            System.out.println("\nВыберите фигуру:");
            System.out.println("  1. Создать круг");
            System.out.println("  2. Создать прямоугольник");
            System.out.println("  3. Создать цилиндр (основание - круг)");
            System.out.println("  4. Создать цилиндр (основание - прямоугольник)");
            System.out.println("  5. Тестирование исключений");
            System.out.println("  0. Выход в меню");

            System.out.print("\nВыбор: ");

            if (!scanner.hasNextInt()) {
                System.out.println("  Ошибка: введите число!");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createCircle();
                    break;
                case 2:
                    createRectangle();
                    break;
                case 3:
                    createCylinderWithCircle();
                    break;
                case 4:
                    createCylinderWithRectangle();
                    break;
                case 5:
                    testGeometryExceptions();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("  Неверный выбор");
            }
        }
    }

    private static void createCircle() {
        System.out.println("\n" + "─".repeat(40));
        System.out.println("СОЗДАНИЕ КРУГА");
        System.out.println("─".repeat(40));

        try {
            System.out.print("Введите радиус: ");
            double radius = scanner.nextDouble();
            scanner.nextLine();
            // Указываем полный путь к классу geometry2d.Circle
            geometry2d.Circle circle = new geometry2d.Circle(radius);
            System.out.println("\nКРУГ СОЗДАН:");
            System.out.printf("  Радиус: %.2f\n", circle.getRadius());
            System.out.printf("  Площадь: %.2f\n", circle.area());
            System.out.printf("  Периметр: %.2f\n", circle.perimeter());
        } catch (geometry2d.exceptions.NegativeValueException e) {
            System.out.println("  Ошибка: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("  Ошибка ввода");
        }
    }

    private static void createRectangle() {
        System.out.println("\n" + "─".repeat(40));
        System.out.println("СОЗДАНИЕ ПРЯМОУГОЛЬНИКА");
        System.out.println("─".repeat(40));

        try {
            System.out.print("Введите ширину: ");
            double width = scanner.nextDouble();
            System.out.print("Введите высоту: ");
            double height = scanner.nextDouble();
            scanner.nextLine();

            // Указываем полный путь к классу geometry2d.Rectangle
            geometry2d.Rectangle rectangle = new geometry2d.Rectangle(width, height);
            System.out.println("\nПрямоугольник создан:");
            System.out.printf("  Ширина: %.2f\n", rectangle.getWidth());
            System.out.printf("  Высота: %.2f\n", rectangle.getHeight());
            System.out.printf("  Площадь: %.2f\n", rectangle.area());
            System.out.printf("  Периметр: %.2f\n", rectangle.perimeter());
        } catch (GeometryException e) {
            System.out.println("  Ошибка: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("  Ошибка ввода");
        }
    }

    private static void createCylinderWithCircle() {
        System.out.println("\n" + "─".repeat(40));
        System.out.println("СОЗДАНИЕ ЦИЛИНДРА (круглое основание)");
        System.out.println("─".repeat(40));

        try {
            System.out.print("Введите радиус основания: ");
            double radius = scanner.nextDouble();
            System.out.print("Введите высоту цилиндра: ");
            double height = scanner.nextDouble();
            scanner.nextLine();

            geometry2d.Circle circle = new geometry2d.Circle(radius);
            geometry3d.Cylinder cylinder = new geometry3d.Cylinder(circle, height);

            System.out.println("\nЦилиндр создан:");
            System.out.printf("  Основание: круг радиусом %.2f\n", circle.getRadius());
            System.out.printf("  Высота: %.2f\n", cylinder.getHeight());
            System.out.printf("  Объем: %.2f\n", cylinder.volume());
        } catch (Exception e) {
            System.out.println("  Ошибка: " + e.getMessage());
        }
    }

    private static void createCylinderWithRectangle() {
        System.out.println("\n" + "─".repeat(40));
        System.out.println("СОЗДАНИЕ ЦИЛИНДРА (прямоугольное основание)");
        System.out.println("─".repeat(40));

        try {
            System.out.print("Введите ширину основания: ");
            double width = scanner.nextDouble();
            System.out.print("Введите высоту основания: ");
            double height = scanner.nextDouble();
            System.out.print("Введите высоту цилиндра: ");
            double cylinderHeight = scanner.nextDouble();
            scanner.nextLine();

            geometry2d.Rectangle rectangle = new geometry2d.Rectangle(width, height);
            geometry3d.Cylinder cylinder = new geometry3d.Cylinder(rectangle, cylinderHeight);

            System.out.println("\nЦилиндр создан:");
            System.out.printf("  Основание: %.2fx%.2f\n", rectangle.getWidth(), rectangle.getHeight());
            System.out.printf("  Высота цилиндра: %.2f\n", cylinder.getHeight());
            System.out.printf("  Объем: %.2f\n", cylinder.volume());
        } catch (Exception e) {
            System.out.println("  Ошибка: " + e.getMessage());
        }
    }

    private static void testGeometryExceptions() {
        System.out.println("\n" + "─".repeat(40));
        System.out.println("ТЕСТИРОВАНИЕ ИСКЛЮЧЕНИЙ");
        System.out.println("─".repeat(40));

        System.out.println("\n1. Круг с отрицательным радиусом:");
        try {
            throw new geometry2d.exceptions.NegativeValueException("Радиус не может быть отрицательным: -5.0");
        } catch (Exception e) {
            System.out.println("  " + e.getMessage());
        }

        System.out.println("\n2. Прямоугольник с отрицательной высотой:");
        try {
            throw new GeometryException("Высота не может быть отрицательной: -6.0");
        } catch (Exception e) {
            System.out.println("  " + e.getMessage());
        }

        System.out.println("\n3. Цилиндр с отрицательной высотой:");
        try {
            throw new geometry3d.exceptions.NegativeValueException("Высота цилиндра не может быть отрицательной: -10.0");
        } catch (Exception e) {
            System.out.println("  " + e.getMessage());
        }

        System.out.println("\nВсе исключения работают корректно!");
    }

    private static void demoHumanCollections() {
        System.out.println("\n" + "═".repeat(60));
        System.out.println("ДЕМОНСТРАЦИЯ 9: HUMAN И КОЛЛЕКЦИИ");
        System.out.println("═".repeat(60));

        HumanCollectionsDemo.demo();
    }

    private static void demoWordFrequency() {
        System.out.println("\n" + "═".repeat(60));
        System.out.println("ДЕМОНСТРАЦИЯ 10: ЧАСТОТА СЛОВ");
        System.out.println("═".repeat(60));

        System.out.println("Введите текст (или 'example' для примера): ");
        String text = scanner.nextLine();

        if (text.equalsIgnoreCase("example")) {
            text = "Привет мир! Привет Java. Программирование на Java - это интересно.";
        }

        Map<String, Integer> frequency = StringProcessor.getWordFrequency(text);
        System.out.println("\nЧастота слов:");
        frequency.forEach((word, count) ->
                System.out.printf("  %s: %d\n", word, count));
    }

    private static void demoMapInversion() {
        System.out.println("\n" + "═".repeat(60));
        System.out.println("ДЕМОНСТРАЦИЯ 11: ИНВЕРСИЯ MAP");
        System.out.println("═".repeat(60));

        Map<String, Integer> original = new HashMap<>();
        original.put("яблоко", 10);
        original.put("банан", 20);
        original.put("апельсин", 30);

        System.out.println("Исходная Map:");
        original.forEach((k, v) -> System.out.println("  " + k + " -> " + v));

        Map<Integer, String> inverted = CollectionsTasks.invertMap(original);

        System.out.println("\nИнвертированная Map:");
        inverted.forEach((k, v) -> System.out.println("  " + k + " -> " + v));
    }
}