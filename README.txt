*************
Project Title
*************

Engine-uity Rebuilds Inventory Manager

*******
Purpose
*******

An application that assists consultants in tracking and scheduling customer appointments.

******
Author
******

Philip Parks

Contact Information:
ppark89@wgu.edu

*******************
Version Information
*******************

Application Version:
1.0

Application Version Date:
20 October 2024

IDE:
IntelliJ IDEA 2024.1.1 (Community Edition)

JDK:
Java SE 17.0.1

JavaFX:
JavaFX-SDK-17.0.1

**********
DIRECTIONS
**********
How to run the program:

Once logged in, the initial view will show a table of all appointments.

~~~ APPOINTMENT TAB ~~~

~ General Appointment Controls
To change the table to a week view, select the 'week' toggle option under the table.
To change the table to a month view, select the 'month' toggle option under the table.
To add an appointment to the database, click on the 'schedule an appointment' button.

~ Appointment Modify/Delete
Select one of the appointments and click on 'view appointment details' to display the details in the quick view area.
From the quick view, click the 'delete' button to remove the appointment from the database.
From the quick view, click the 'modify' button to update an appointment record.

~~~ REPORTS TAB ~~~

Click on the 'Reports' tab at the top left of the main screen to view the reporting controls.
Report 1: Select an appointment 'Type' and 'Month' from the respective combo box and click the 'Total' button.
Report 2: Select a 'Contact' from the combo box under the table to display appointments for that contact in the table.
Report 3: Select a 'Location' to display how many appointments are at the selected location.

~~~ CUSTOMER TAB ~~~

Click on the 'Customer' tab at the top left of the main screen to view the customer controls.
To add a customer, click on the 'Add a Customer' button located on the bottom right of the screen.
Select a customer and click on view details to display the customer details in the quick view are.
From the quick view, click the 'delete' button to remove the customer from the database.
From the quick view, click the 'modify' button to update a customers' information.

~~~ LOGGING OUT ~~~

To log out of the application and return to the login screen, click on the 'Log out' button located on the bottom right
of the screen.

********************************
DESCRIPTION OF ADDITIONAL REPORT
********************************

Reports the total appointments associated with a chosen location. The choice is made via a combo box.

***********************
MySQL Connector Version
***********************

mysql-connector-java-8.0.25
