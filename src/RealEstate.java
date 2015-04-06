/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DELL
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;
import houseDetails.*;
import CommonList.*;
import CommonList.SortedList;
public class RealEstate {
 // The list of house information
private static CommonList.SortedList list = new CommonList.SortedList();

private static JTextField txt_lot; 
private static JTextField txt_first; 
private static JTextField txt_last; 
private static JTextField txt_price; 
private static JTextField txt_feet; 
private static JTextField txt_bed; 

private static JLabel lbl_show;//status lable

private static void showHouse(ListHouse house)
{
txt_lot.setText(Integer.toString(house.lotNumber()));
txt_first.setText(house.firstName());
txt_last.setText(house.lastName());
txt_price.setText(Integer.toString(house.price()));
txt_feet.setText(Integer.toString(house.squareFeet()));
txt_bed.setText(Integer.toString(house.bedRooms()));
}

private static ListHouse getHouse()
{
String lastName;
String firstName;
int lotNumber;
int price;
int squareFeet;
int bedRooms;
lotNumber = Integer.parseInt(txt_lot.getText());
firstName = txt_first.getText();
lastName = txt_last.getText();
price = Integer.parseInt(txt_price.getText());
squareFeet = Integer.parseInt(txt_feet.getText());
bedRooms = Integer.parseInt(txt_bed.getText());
ListHouse house = new ListHouse(lastName, firstName, lotNumber, price,
squareFeet, bedRooms);
return house;
}
// Clears house infor
private static void clearHouse()
{
txt_lot.setText("");
txt_first.setText("");
txt_last.setText("");
txt_price.setText("");
txt_feet.setText("");
txt_bed.setText("");
}

private static class ActionHandler implements ActionListener
{
public void actionPerformed(ActionEvent event)

{
ListHouse house;
if (event.getActionCommand().equals("Reset"))
{ 
list.reset();
if (list.lengthIs() == 0)
clearHouse();
else
{
house = (ListHouse)list.getNextItem();
showHouse(house);
}
lbl_show.setText("List reset");
}
else
if (event.getActionCommand().equals("Next"))
{ 
if (list.lengthIs() == 0)
lbl_show.setText("list is empty");
else
{
house = (ListHouse)list.getNextItem();
showHouse(house);
lbl_show.setText("Next house displayed");
}
}
else
if (event.getActionCommand().equals("Add"))
{ 
try
{
house = getHouse();
if (list.isThere(house))
lbl_show.setText("Lot number already exists");
else
{
list.insert(house);
lbl_show.setText("Successfully added");
}
}
catch (NumberFormatException badHouseData)
{

lbl_show.setText("Data not in a correct format");
}
}
else
if (event.getActionCommand().equals("Delete"))
{
try
{
house = getHouse();
if (list.isThere(house))
{
list.delete(house);
lbl_show.setText("House successfully deleted");
}
else
lbl_show.setText("Lot number not on list");
}
catch (NumberFormatException badHouseData)
{

lbl_show.setText("Data not in a correct format");
}
}
else
if (event.getActionCommand().equals("Clear"))
{ 
clearHouse();
lbl_show.setText(list.lengthIs() + " houses on list");
}
else
if (event.getActionCommand().equals("Find"))
{ 
int lotNumber;
try
    {
lotNumber = Integer.parseInt(txt_lot.getText());
house = new ListHouse("", "", lotNumber, 0, 0, 0);
if (list.isThere(house))
{
house = (ListHouse)list.retrieve(house);
showHouse(house);
lbl_show.setText("House found");
}
else
lbl_show.setText("House not found");
}
catch (NumberFormatException badHouseData)
{

lbl_show.setText(" Data not in a correct format");
}
}
}
}
public static void main(String args[]) throws IOException
{
ListHouse house;
char command;
int length;
JLabel blankLabel; 
JLabel lotLabel; 
JLabel firstLabel;
JLabel lastLabel;
JLabel priceLabel;
JLabel feetLabel;
JLabel bedLabel;

JButton reset;
JButton next; 
JButton add; 
JButton delete; 
JButton clear; 
JButton find; 
ActionHandler action; 

JFrame displayFrame = new JFrame();
displayFrame.setTitle("Real Estate Program");
displayFrame.setSize(350,400);
displayFrame.addWindowListener(new WindowAdapter() 

{
public void windowClosing(WindowEvent event)
{
JFrame displayFrame = new JFrame();
ListHouse house;
displayFrame.dispose(); 
try
{
//To store info from list into house file
HouseFile.rewrite();
list.reset();
int length = list.lengthIs();
for (int counter = 1; counter <= length; counter++)
{
house = (ListHouse)list.getNextItem();
HouseFile.putToFile(house);
}
HouseFile.close();
}
catch (IOException fileCloseProblem)
{
System.out.println("Exception raised concerning the house info file "
+ "upon program termination");
}
System.exit(0); 
}
});

Container contentPane = displayFrame.getContentPane();
JPanel infoPanel = new JPanel();

lbl_show= new JLabel("", JLabel.CENTER);
lbl_show.setBorder(new LineBorder(Color.blue));
blankLabel = new JLabel("");
lotLabel = new JLabel("Lot Number: ", JLabel.RIGHT);
txt_lot= new JTextField("", 15);
firstLabel = new JLabel("First Name: ", JLabel.RIGHT);
txt_first = new JTextField("", 15);
lastLabel = new JLabel("Last Name: ", JLabel.RIGHT);
txt_last = new JTextField("", 15);
priceLabel = new JLabel("Price: ", JLabel.RIGHT);
txt_price = new JTextField("", 15);
feetLabel = new JLabel("Square Feet: ", JLabel.RIGHT);
txt_feet = new JTextField("", 15);
bedLabel = new JLabel("Number of Bedrooms: ", JLabel.RIGHT);
txt_bed = new JTextField("", 15);

reset = new JButton("Reset");
next = new JButton("Next");
add = new JButton("Add");
delete = new JButton("Delete");
clear = new JButton("Clear");
find = new JButton("Find");

action = new ActionHandler();
reset.addActionListener(action);
next.addActionListener(action);
add.addActionListener(action);
delete.addActionListener(action);
clear.addActionListener(action);
find.addActionListener(action);

HouseFile.reset();
while (HouseFile.moreHouses())
{
house = HouseFile.getNextHouse();
list.insert(house);
}

list.reset();
if (list.lengthIs() != 0)
{
house = (ListHouse)list.getNextItem();
showHouse(house);
}

lbl_show.setText(list.lengthIs() + " houses on list ");

infoPanel.setLayout(new GridLayout(10,2));
infoPanel.add(lbl_show);
infoPanel.add(blankLabel);
infoPanel.add(lotLabel);
infoPanel.add(txt_lot);
infoPanel.add(firstLabel);
infoPanel.add(txt_first);
infoPanel.add(lastLabel);
infoPanel.add(txt_last);
infoPanel.add(priceLabel);
infoPanel.add(txt_price);
infoPanel.add(feetLabel);
infoPanel.add(txt_feet);
infoPanel.add(bedLabel);
infoPanel.add(txt_bed);
infoPanel.add(reset);
infoPanel.add(next);
infoPanel.add(add);
infoPanel.add(delete);
infoPanel.add(clear);
infoPanel.add(find);
contentPane.add(infoPanel);
displayFrame.show();

}    
}
