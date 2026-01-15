Для корректной работы надо нажать кнопку current file и выбрать edit configuration, потом надо создать новую конфигурацию application имя и main class называем main.
Далее нажимаем на Modify options и ищем Add VM options, в VM options вводим:
--module-path "C:\Users\Malakel\.m2\repository\org\openjfx\javafx-base\21\javafx-base-21-win.jar;C:\Users\Malakel\.m2\repository\org\openjfx\javafx-controls\21\javafx-controls-21-win.jar;C:\Users\Malakel\.m2\repository\org\openjfx\javafx-graphics\21\javafx-graphics-21-win.jar;C:\Users\Malakel\.m2\repository\org\openjfx\javafx-fxml\21\javafx-fxml-21-win.jar" --add-modules javafx.controls,javafx.fxml
Вместо "Malakel" пишем имя вашего компьютера.
Потом пишем в Program arguments --gui, после нажимаем Apply для сохранения.
Далее можно запускать программу, если убрать --gui то программа откроется в консоли.
