# Warehouse Management Android App
The system developed for the needs of the present project is classified in the type "Autonomous Warehouse Management Systems", as the main feature of this type is its simplicity in terms of the management features it offers. More specifically, the purpose of the application is the management of a food warehouse, which monitors the processes of receiving and sending packages. Through the application, the user can scan the Quick Response Code (QR Code) located outside the box, thus adding the stock of the product to the local database of the warehouse, but also to the global database (in case this warehouse is a branch of a main one).

The diagram below shows a typical use of the app, combining its functions and the ways in which they interact with the user's decisions.

![flowchart](https://user-images.githubusercontent.com/91207835/203397185-b313f018-9e28-4053-a6de-93ff1e2af7c4.png)

In the design of the application, particular attention was paid to simplifying the user interface, with a view to enabling the user to become immediately familiar with it. More specifically, the menu was placed at the bottom of the application, as it is the closest point to the palm of the hand and thus facilitates the user, since he does not have to make large movements with his thumb. Also worth mentioning, it is the automatic switching between the QR code scanner and the list of products immediately after scanning a code, because it speeds up the production process by allowing the user to check if the product data entered into the database is correct.

Below is the final design of the app on a Storyboard.

![image](https://user-images.githubusercontent.com/91207835/203397866-e8bcc204-745c-468e-a904-f12ca961853a.png)

**Running the application**

1. Xampp or similar app should be installed.
2. Creation of the database in the phpMyAdmin application with the following SQL queries
```
CREATE DATABASE IF NOT EXISTS `warehouse`;
USE `warehouse`;

CREATE TABLE IF NOT EXISTS `products` (
`PID` int(11) NOT NULL AUTO_INCREMENT,
`PSerialNo` int(4) NOT NULL,
`PName` text NOT NULL,
`Price` decimal(10,1) NOT NULL,
`PQnt` int(10) NOT NULL,
PRIMARY KEY (`PID`) );
```
3. Copy the CRUDAPI folder to the Xampp htdocs folder and modify the database login details of the php files contained in the folder to connect to the local database.
4. Lines 28 and 83 of the code of the GlobalDBHandler.java indicate the IP address of the connection (in this case it is 192.168.1.4). In place of the existing one, the IP address of the connection of the PC on which the application is to be run must be placed.


**Known Issues**
- Some code-level issues occurred during the development of the application. One of them occurred as a result of the operation to delete a record from the local database. More specifically, when the user deletes a record through the application, it will be removed from both the local and the global database. The result of this is the confusion between the databases in the case of a second local database in a different branch of the main warehouse, since if e.g. there are 1000 pieces of a product in total (from 500 in each branch) and an employee deletes the registration in the local database, then from the global database the total of the pieces located in the second branch will be deleted. The solution to this problem is to update the quantity in the global database when deleting on a local database and then checking the remaining quantity. If the remaining quantity is equal to 0, then the record is also deleted from the global database. 
- When you switch to one tab, the home tab still appears to be active.
- When the user has dark mode enabled, the color of the icons and text of the menu buttons changes from white to black, resulting in no contrast with their surroundings and therefore they are not visible.
