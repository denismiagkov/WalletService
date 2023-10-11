# Wallet-Service
Для запуска проекта:
1. Скачайте jar-файл;
# 2. Распакуйте jar-файл;
# 3. В меню Intellij Idea File выберите:
# File -> Open -> [sourcepath - название  директории с распакованным архивом] -> ОК

В связи с тем, что возможны конфликты версий JDK лучше использовать Docker:
2. Создайте в рабочем пространстве папку test.
3. Распакуйте в папке test jar-архив в папку src.
4. Скопируйте в папку test Dockerfile из проекта.
5. Откройте терминал из папки test.
6. Введите в терминал команду: sudo docker build -t my-wallet-service .
7. Введите в терминал команду: sudo docker run -it --rm my-wallet-service
8. Введите в терминал команду: sudo docker run -it --rm my-wallet-service
Приложение должно быть запущено.
