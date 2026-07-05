import java.sql.*;
import java.util.*;
import javax.swing.table.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;
import java.io.*;
import java.text.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

class City
{
private int cityCode;
private String name;
private int stateCode;
public City()
{
this.cityCode=0;
this.name="";
this.stateCode=0;
}
public void setCityCode(int cityCode)
{
this.cityCode=cityCode;
}
public void setName(String name)
{
this.name=name;
}
public void setStateCode(int stateCode)
{
this.stateCode=stateCode;
}
public int getCityCode()
{
return this.cityCode;
}
public String getName()
{
return this.name;
}
public int getStateCode()
{
return this.stateCode;
}

}

class CityModel
{
private java.util.List<City> cities;
public CityModel()
{
this.populateCities();
}
public void populateCities()
{
// jdbc code to fetch data from city table and populate cities list
cities=new ArrayList<City>();
try
{
Connection connection;
connection=DriverManager.getConnection(DBConfig.url,DBConfig.user,DBConfig.password);
Statement statement;
statement=connection.createStatement();
ResultSet resultSet;
resultSet=statement.executeQuery("select * from city order by city.name");
City city;
while(resultSet.next())
{
city=new City();
city.setCityCode(resultSet.getInt("code"));
city.setName(resultSet.getString("name").trim());
city.setStateCode(resultSet.getInt("state_code"));
cities.add(city);
}
resultSet.close();
statement.close();
connection.close();
}catch(Exception exception)
{
JOptionPane.showMessageDialog(null,"Failed to load data from the database.\n\nMake sure MySQL is running and 'db.properties' is configured correctly.\n\nDetails: " + exception.getMessage(),"Database Error",JOptionPane.ERROR_MESSAGE);
System.exit(0); //application ends
}
}
public java.util.List<City> getCities()
{
if(this.cities.size()==0) return null;
return this.cities;
}

}

class Employee
{
private String empId;
private String name;
private java.sql.Date dateOfBirth;
private String cityName;
private char gender;
private int salary;
public Employee()
{
this.empId="";
this.name="";
this.dateOfBirth=null;
this.cityName="";
this.gender=(char)0;
this.salary=0;
}
public void setEmpId(String empId)
{
this.empId=empId;
}
public String getEmpId()
{
return this.empId;
}
public void setName(String name)
{
this.name=name;
}
public String getName()
{
return this.name;
}
public void setGender(char gender)
{
this.gender=gender;
}
public char getGender()
{
return this.gender;
}
public void setDateOfBirth(java.sql.Date dateOfBirth)
{
this.dateOfBirth=dateOfBirth;
}
public java.sql.Date getDateOfBirth()
{
return this.dateOfBirth;
}
public void setCityName(String cityName)
{
this.cityName=cityName;
}
public String getCityName()
{
return this.cityName;
}
public void setSalary(int salary)
{
this.salary=salary;
}
public int getSalary()
{
return this.salary;
}
}




class ModelException extends Exception
{
public ModelException(String message)
{
super(message);
}
}


class EmployeeModel extends AbstractTableModel
{
private java.util.List<Employee> employees;
private String [] titles;
public EmployeeModel()
{
this.populateEmployees();
}
public void populateEmployees()
{
this.titles=new String[3];
titles[0]="S.No.";
titles[1]="Id.";
titles[2]="Name";
// jdbc code to fetch data from employee table and populate employees list
employees=new ArrayList<Employee>();
try
{
Connection connection;
connection=DriverManager.getConnection(DBConfig.url,DBConfig.user,DBConfig.password);
Statement statement;
statement=connection.createStatement();
ResultSet resultSet;
resultSet=statement.executeQuery("select employee.emp_id,employee.name, employee.date_of_birth,city.name as city_name,employee.gender,employee.salary from employee inner join city on employee.city_code=city.code order by employee.name");
Employee employee;
while(resultSet.next())
{
employee=new Employee();
employee.setEmpId(resultSet.getString("emp_id").trim());
employee.setName(resultSet.getString("name").trim());
employee.setDateOfBirth(resultSet.getDate("date_of_birth"));
employee.setCityName(resultSet.getString("city_name").trim());
employee.setGender(resultSet.getString("gender").charAt(0));
employee.setSalary(resultSet.getInt("salary"));
employees.add(employee);
}
resultSet.close();
statement.close();
connection.close();
}catch(Exception exception)
{
JOptionPane.showMessageDialog(null,"Failed to load data from the database.\n\nMake sure MySQL is running and 'db.properties' is configured correctly.\n\nDetails: " + exception.getMessage(),"Database Error",JOptionPane.ERROR_MESSAGE);
System.exit(0); //application ends
}
}
public Employee getEmployee(int index)
{
	if(index<0 || index>=this.employees.size()) return null;
	return this.employees.get(index);
}
public int getRowCount()
{
	return this.employees.size();
}
public int getColumnCount()
{
	return this.titles.length;
}
public String getColumnName(int columnIndex)
{
	return this.titles[columnIndex];
}
public boolean isCellEditable(int rowIndex, int columnIndex)
{
	return false;
}
public Object getValueAt(int rowIndex,int columnIndex)
{
	if(columnIndex==0) return rowIndex+1;
	else if(columnIndex==1) return employees.get(rowIndex).getEmpId();
	else return employees.get(rowIndex).getName();
}
public Class getColumnClass(int columnIndex)
{
	if(columnIndex==0) return Integer.class;
	else if(columnIndex==1) return String.class;
	else return String.class;
}

public int addEmployee(Employee employee) throws ModelException
{
try
{
Employee e;
for(int i=0;i<employees.size();i++)
{
e=employees.get(i);
if(e.getEmpId().equalsIgnoreCase(employee.getEmpId())) throw new ModelException("Employee Id: "+employee.getEmpId()+" exists.");
}
Connection connection=DriverManager.getConnection(DBConfig.url,DBConfig.user,DBConfig.password);
PreparedStatement preparedStatement=connection.prepareStatement("select code from city where name like ?");
preparedStatement.setString(1,employee.getCityName());
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new ModelException("Invalid city : "+employee.getCityName());
}
int cityCode=resultSet.getInt("code");
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("insert into employee values(?,?,?,?,?,?)");
preparedStatement.setString(1,employee.getEmpId());
preparedStatement.setString(2,employee.getName());
preparedStatement.setDate(3,employee.getDateOfBirth());
preparedStatement.setInt(4,cityCode);
preparedStatement.setString(5,String.valueOf(employee.getGender()));
preparedStatement.setInt(6,employee.getSalary());
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
int index=0;
for(Employee emp : employees)
{
if(employee.getName().compareToIgnoreCase(emp.getName())<0){
break;
}
index++;
}
this.employees.add(index, employee);
return index;

}catch(SQLException sqlException)
{
throw new ModelException(sqlException.getMessage());
}
catch(Exception e)
{
throw new ModelException(e.getMessage());
}
}

public int updateEmployee(Employee employee) throws ModelException
{
try
{
Connection connection=DriverManager.getConnection(DBConfig.url,DBConfig.user,DBConfig.password);
PreparedStatement preparedStatement=connection.prepareStatement("select code from city where name like ?");
preparedStatement.setString(1,employee.getCityName());
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new ModelException("Invalid city : "+employee.getCityName());
}
int cityCode=resultSet.getInt("code");
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("update employee set name=?, date_of_birth=?, city_code=?, gender=?, salary=? where emp_id=?");
preparedStatement.setString(1,employee.getName());
preparedStatement.setDate(2,employee.getDateOfBirth());
preparedStatement.setInt(3,cityCode);
preparedStatement.setString(4,String.valueOf(employee.getGender()));
preparedStatement.setInt(5,employee.getSalary());
preparedStatement.setString(6,employee.getEmpId());
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();

int index=0;
for(Employee emp : employees)
{
if(employee.getEmpId().equalsIgnoreCase(emp.getEmpId())) 
break;
index++;
}
employees.remove(index);

index=0;
for(Employee emp : employees)
{
if(employee.getName().compareToIgnoreCase(emp.getName())<0) 
break;
index++;
}
employees.add(index,employee);
return index;
}catch(SQLException sqlException)
{
throw new ModelException(sqlException.getMessage());
}
catch(Exception e)
{
throw new ModelException(e.getMessage());
}

}

public void deleteEmployee(Employee employee) throws ModelException
{
System.out.println("deleteEmployee called");
if(employee==null) return;
try
{
Connection connection=DriverManager.getConnection(DBConfig.url,DBConfig.user,DBConfig.password);
PreparedStatement preparedStatement=connection.prepareStatement("delete from employee where emp_id = ?");
preparedStatement.setString(1,employee.getEmpId());
int rowsAffected=preparedStatement.executeUpdate();
if(rowsAffected<=0) throw new ModelException("Error Occured. Employee is not deleted");
preparedStatement.close();
connection.close();

int index=0;
for(Employee emp : employees)
{
if(emp.getEmpId().equalsIgnoreCase(employee.getEmpId()))
{
break;
}
index++;
}
if(index==employees.size()) throw new ModelException("EmpId doesn't exist");
employees.remove(index);
}catch(SQLException sqlException)
{
throw new ModelException(sqlException.getMessage());
}
catch(Exception e)
{
throw new ModelException(e.getMessage());
}

}

public int getIndexOfEmployeeWhoseEmpIdStartsWith(String empId)
{
if(empId==null) return -1;
int index=0;
for(Employee emp : employees)
{
if(emp.getEmpId().toUpperCase().startsWith(empId.toUpperCase())) return index;
index++;
}
return -1;
}

public int getIndexOfEmployeeWhoseNameStartsWith(String name)
{
if(name.length()==-1) return -1;
int index=0;
for(Employee emp : employees)
{
if(emp.getName().toUpperCase().startsWith(name.toUpperCase())) return index;
index++;
}
return -1;
}

public void exportToPDF(JTable employeesTable,String inputFileAbsolutePath) throws ModelException
{
File file=new File(inputFileAbsolutePath);
try
{
Document document = new Document();
PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
writer.setPageEvent(new PdfPageEventHelper() {

public void onStartPage(PdfWriter writer,Document document)
{
PdfContentByte canvas=writer.getDirectContent();
float x = document.getPageSize().getLeft()+document.getPageSize().getRight() / 2;
ColumnText.showTextAligned(canvas,Element.ALIGN_CENTER,new Paragraph("Employee Table Report",new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA,16,com.itextpdf.text.Font.BOLD)),x,document.getPageSize().getTop()-50,0);
ColumnText.showTextAligned(canvas,Element.ALIGN_CENTER,new Paragraph("--------------------------------------------------------------------------------------------------------",new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA,12,com.itextpdf.text.Font.BOLD)),x,document.getPageSize().getTop()-67,0);
}

public void onEndPage(PdfWriter writer,Document document)
{
PdfContentByte canvas=writer.getDirectContent();
float x = document.getPageSize().getLeft()+document.getPageSize().getRight() / 2;
ColumnText.showTextAligned(canvas,Element.ALIGN_CENTER,new Paragraph("--------------------------------------------------------------------------------------------------------",new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA,12,com.itextpdf.text.Font.BOLD)),x,document.getPageSize().getBottom()+63,0);
/*
canvas.moveTo(document.getPageSize().getLeft()-70, document.getPageSize().getBottom()+66);
canvas.lineTo(document.getPageSize().getRight()+70, document.getPageSize().getBottom()+66);
canvas.stroke();
*/
ColumnText.showTextAligned(canvas,Element.ALIGN_CENTER,new Paragraph("Generated on: " + new java.util.Date(),new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA,12,com.itextpdf.text.Font.NORMAL)),x,document.getPageSize().getBottom()+53,0);
ColumnText.showTextAligned(canvas,Element.ALIGN_CENTER,new Paragraph(String.valueOf(writer.getPageNumber())),x,document.getPageSize().getBottom()+20,0);
}

});

document.open();

PdfPTable pdfTable = null;
int pageSize=40;
boolean prepareHeading=true;
int totalColumns=employeesTable.getColumnCount();
int totalRows=employeesTable.getRowCount();
//int sno=1;
for (int rows = 0; rows < totalRows; rows++) {
if(prepareHeading)
{
pdfTable = new PdfPTable(totalColumns);
float colWidth[]={50f,50f,140f};
pdfTable.setWidths(colWidth);

//Paragraph title=new Paragraph("Employee Table Report",new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA,16,com.itextpdf.text.Font.BOLD));
//title.setAlignment(Element.ALIGN_CENTER);
//document.add(title);
document.add(new Paragraph(" "));
document.add(new Paragraph(" "));
document.add(new Paragraph(" "));
// Add table headers
for (int i = 0; i < totalColumns; i++)
{
PdfPCell cell=new PdfPCell(new Phrase(employeesTable.getColumnName(i),new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA,com.itextpdf.text.Font.DEFAULTSIZE,com.itextpdf.text.Font.BOLD)));
cell.setHorizontalAlignment(Element.ALIGN_CENTER);
pdfTable.addCell(cell);
}
prepareHeading=false;
}
// Add rows
for (int cols = 0; cols < totalColumns; cols++) {
PdfPCell cell=new PdfPCell(new Phrase(employeesTable.getValueAt(rows,cols).toString()));
cell.setHorizontalAlignment(Element.ALIGN_CENTER);
pdfTable.addCell(cell);
}
if((rows+1)%pageSize==0 || (rows+1)==totalRows)
{
document.add(pdfTable);
//document.add(new Paragraph("Generated on: " + new java.util.Date()));
prepareHeading=true;
if((rows+1)!=totalRows) document.newPage();
}
//sno++;
}
document.close();

// opening pdf after creating pdf
if(Desktop.isDesktopSupported()) Desktop.getDesktop().open(file);
else System.out.println("Desktop not supported");

}catch(Exception e){
if(file.exists()) file.delete();
throw new ModelException(e.getMessage());
}

}


}//class employeemodel ends







class EmployeeDetailPanel extends JPanel
{
private Employee employee=null;
private JLabel idCaptionLabel;
private JLabel idLabel;
private JLabel nameCaptionLabel;
private JLabel nameLabel;
private JLabel genderCaptionLabel;
private JLabel genderLabel;
private JLabel dateOfBirthCaptionLabel;
private JLabel dateOfBirthLabel;
private JLabel cityCaptionLabel;
private JLabel cityLabel;
private JLabel salaryCaptionLabel;
private JLabel salaryLabel;
public EmployeeDetailPanel()
{
idCaptionLabel=new JLabel("Emp.Id. : ");
idLabel=new JLabel("");
nameCaptionLabel=new JLabel("Name : ");
nameLabel=new JLabel("");
genderCaptionLabel=new JLabel("Gender : ");
genderLabel=new JLabel("");
dateOfBirthCaptionLabel=new JLabel("Date of birth : ");
dateOfBirthLabel=new JLabel("");
cityCaptionLabel=new JLabel("City : ");
cityLabel=new JLabel("");
salaryCaptionLabel=new JLabel("Salary : ");
salaryLabel=new JLabel("");
setLayout(new GridLayout(6,2));
add(idCaptionLabel);
add(idLabel);
add(nameCaptionLabel);
add(nameLabel);
add(genderCaptionLabel);
add(genderLabel);
add(dateOfBirthCaptionLabel);
add(dateOfBirthLabel);
add(cityCaptionLabel);
add(cityLabel);
add(salaryCaptionLabel);
add(salaryLabel);
idCaptionLabel.setFont(new java.awt.Font("Times New Roman",java.awt.Font.BOLD,16));
idLabel.setFont(new java.awt.Font("Times New Roman",java.awt.Font.PLAIN,16));
nameCaptionLabel.setFont(new java.awt.Font("Times New Roman",java.awt.Font.BOLD,16));
nameLabel.setFont(new java.awt.Font("Times New Roman",java.awt.Font.PLAIN,16));
genderCaptionLabel.setFont(new java.awt.Font("Times New Roman",java.awt.Font.BOLD,16));
genderLabel.setFont(new java.awt.Font("Times New Roman",java.awt.Font.PLAIN,16));
dateOfBirthCaptionLabel.setFont(new java.awt.Font("Times New Roman",java.awt.Font.BOLD,16));
dateOfBirthLabel.setFont(new java.awt.Font("Times New Roman",java.awt.Font.PLAIN,16));
cityCaptionLabel.setFont(new java.awt.Font("Times New Roman",java.awt.Font.BOLD,16));
cityLabel.setFont(new java.awt.Font("Times New Roman",java.awt.Font.PLAIN,16));
salaryCaptionLabel.setFont(new java.awt.Font("Times New Roman",java.awt.Font.BOLD,16));
salaryLabel.setFont(new java.awt.Font("Times New Roman",java.awt.Font.PLAIN,16));

idCaptionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
nameCaptionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
genderCaptionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
dateOfBirthCaptionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
cityCaptionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
salaryCaptionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
idLabel.setHorizontalAlignment(SwingConstants.LEFT);
nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
genderLabel.setHorizontalAlignment(SwingConstants.LEFT);
dateOfBirthLabel.setHorizontalAlignment(SwingConstants.LEFT);
cityLabel.setHorizontalAlignment(SwingConstants.LEFT);
salaryLabel.setHorizontalAlignment(SwingConstants.LEFT);
}

public void setEmployee(Employee employee)
{
idLabel.setText("");
nameLabel.setText("");
genderLabel.setText("");
cityLabel.setText("");
dateOfBirthLabel.setText("");
salaryLabel.setText("");

idLabel.setText(employee.getEmpId());
nameLabel.setText(employee.getName());
if(employee.getGender()=='M') genderLabel.setText("Male");
if(employee.getGender()=='F') genderLabel.setText("Female");
cityLabel.setText(employee.getCityName());
salaryLabel.setText(String.valueOf(employee.getSalary()));
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd MMM yyyy,   hh:mm:ss");
dateOfBirthLabel.setText(simpleDateFormat.format(employee.getDateOfBirth()));
}

public void clear()
{
this.employee=null;
idLabel.setText("");
nameLabel.setText("");
genderLabel.setText("");
cityLabel.setText("");
dateOfBirthLabel.setText("");
salaryLabel.setText("");
}
}









class EmployeeCRUDUI extends JFrame implements ListSelectionListener,ActionListener,DocumentListener
{
private EmployeeModel employeeModel; 
private JTable employeesTable;
private JScrollPane employeesTablePane; 
private Container container;
private EmployeeDetailPanel employeeDetailPanel;
private ActionButtonPanel actionButtonPanel;
private SouthPanel southPanel;
private JButton addButton;
private JButton editButton;
private JButton deleteButton;
private AddPanel addPanel;
private EditPanel editPanel;
private JLabel searchLabel;
private JTextField searchTextField;
private ButtonGroup searchByButtonGroup;
private JRadioButton searchByEmpIdRadioButton;
private JRadioButton searchByNameRadioButton;
private JPanel searchPanel;
private JButton exportToPdfButton;


class ActionButtonPanel extends JPanel
{
ActionButtonPanel()
{
setLayout(new GridLayout(1,7));
add(new JLabel(" "));
add(addButton);
add(new JLabel(" "));
add(editButton);
add(new JLabel(" "));
add(deleteButton);
add(new JLabel(""));
}
}

class SouthPanel extends JPanel
{
SouthPanel()
{
setLayout(new BorderLayout());
add(employeeDetailPanel,BorderLayout.CENTER);
add(actionButtonPanel,BorderLayout.SOUTH);
}
}
 

public EmployeeCRUDUI()
{
super("Employeees Master");
employeeModel=new EmployeeModel();
employeesTable=new JTable(employeeModel);
employeesTable.setRowHeight(30);
employeesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
employeesTable.getColumnModel().getColumn(0).setPreferredWidth(100);
employeesTable.getColumnModel().getColumn(1).setPreferredWidth(200);
employeesTable.getColumnModel().getColumn(2).setPreferredWidth(500);
employeesTable.getTableHeader().setResizingAllowed(false);                  
employeesTable.getTableHeader().setReorderingAllowed(false);
employeesTable.setFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 14)); // 14 is the font size of data
employeesTable.getTableHeader().setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 14));  //setting font size of column header
employeesTable.getColumnModel().getColumn(0).setCellRenderer(
new DefaultTableCellRenderer(){{
setHorizontalAlignment(SwingConstants.CENTER);
}}
);
employeesTable.getColumnModel().getColumn(1).setCellRenderer(
new DefaultTableCellRenderer(){{
setHorizontalAlignment(SwingConstants.CENTER);
}}
);employeesTable.getColumnModel().getColumn(2).setCellRenderer(
new DefaultTableCellRenderer(){{
setHorizontalAlignment(SwingConstants.CENTER);
}}
);


employeesTablePane=new JScrollPane(employeesTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

employeeDetailPanel=new EmployeeDetailPanel();

addButton=new JButton("Add");
editButton=new JButton("Edit");
deleteButton=new JButton("Delete");
actionButtonPanel=new ActionButtonPanel();

southPanel=new SouthPanel();

searchLabel=new JLabel("Search");
searchByButtonGroup=new ButtonGroup();
searchByEmpIdRadioButton=new JRadioButton("Emp.Id");
searchByNameRadioButton=new JRadioButton("Name",true);
searchByButtonGroup.add(searchByEmpIdRadioButton);
searchByButtonGroup.add(searchByNameRadioButton);
searchTextField=new JTextField(50);
searchTextField.getDocument().addDocumentListener(this);
exportToPdfButton=new JButton("Export to pdf",new ImageIcon((new ImageIcon("resources/exportToPdfLogo.png")).getImage().getScaledInstance(20,20,java.awt.Image.SCALE_SMOOTH)));

searchPanel=new JPanel();
searchPanel.setLayout(new FlowLayout());
searchPanel.add(searchLabel);
searchPanel.add(searchByEmpIdRadioButton);
searchPanel.add(searchByNameRadioButton);
searchPanel.add(searchTextField);
searchPanel.add(Box.createRigidArea(new Dimension(100, 0)));
searchPanel.add(exportToPdfButton);

container=getContentPane();
container.setLayout(new BorderLayout());
container.add(searchPanel,BorderLayout.NORTH);
container.add(employeesTablePane,BorderLayout.CENTER);
container.add(southPanel,BorderLayout.SOUTH);
Dimension desktopDimension=Toolkit.getDefaultToolkit().getScreenSize();
this.setSize(1000,700);
this.setLocation(desktopDimension.width/2-this.getWidth()/2,desktopDimension.height/2-this.getHeight()/2);
this.setVisible(true);
this.setDefaultCloseOperation(EXIT_ON_CLOSE);

addEventListeners();
}
public void changedUpdate(DocumentEvent de)
{
performSearch();
}
public void removeUpdate(DocumentEvent de)
{
performSearch();
}
public void insertUpdate(DocumentEvent de)
{
performSearch();
}
private void performSearch()
{
searchTextField.setForeground(Color.black);
String whatToSearch=searchTextField.getText().trim();
if(whatToSearch.length()==0) return;
int index;
if(searchByEmpIdRadioButton.isSelected())
{
index=employeeModel.getIndexOfEmployeeWhoseEmpIdStartsWith(whatToSearch);
if(index==-1)
{
searchTextField.setForeground(Color.red);
employeesTable.clearSelection();
return;
}
employeesTable.setRowSelectionInterval(index,index);
employeesTable.scrollRectToVisible(employeesTable.getCellRect(index,index,true));
}


if(searchByNameRadioButton.isSelected())
{
index=employeeModel.getIndexOfEmployeeWhoseNameStartsWith(whatToSearch);
if(index==-1)
{
searchTextField.setForeground(Color.red);
employeesTable.clearSelection();
return;
}
employeesTable.setRowSelectionInterval(index,index);
employeesTable.scrollRectToVisible(employeesTable.getCellRect(index,index,true));
}


}

public void valueChanged(ListSelectionEvent event)
{
int x=employeesTable.getSelectedRow();
Employee employee=employeeModel.getEmployee(x);
if(employee==null) return;
employeeDetailPanel.setEmployee(employee);
}

public void actionPerformed(ActionEvent ae)
{
if(ae.getSource()==addButton)
{
this.container.remove(southPanel);
this.employeesTable.clearSelection();
this.employeesTable.setEnabled(false);

this.addPanel=new AddPanel();
this.container.add(addPanel,BorderLayout.SOUTH);

container.revalidate();
container.repaint();
}
if(ae.getSource()==editButton) 
{
int x=employeesTable.getSelectedRow();
if(x==-1)
{
JOptionPane.showMessageDialog(this,"Select an employee to Edit");
return;
}
this.employeesTable.setEnabled(false);
this.editPanel = new EditPanel();
container.remove(southPanel);
container.add(editPanel, BorderLayout.SOUTH);
container.revalidate();
container.repaint();
}

if(ae.getSource()==deleteButton)
{
int x=employeesTable.getSelectedRow();
if(x==-1)
{
JOptionPane.showMessageDialog(this,"Select an employee to Delete");
return;
}
DeletePanel deletePanel = new DeletePanel();
}

if(ae.getSource()==exportToPdfButton)
{
JFileChooser jfc=new JFileChooser();
jfc.addChoosableFileFilter(new FileNameExtensionFilter("PDF Files (*.pdf)", "pdf"));
jfc.setCurrentDirectory(new File("."));
//jfc.setSelectedFile(new File("10"));       // needs to be removed

String inputFileName=null;
String inputFileAbsolutePath=null;
if(jfc.showSaveDialog(this)==JFileChooser.APPROVE_OPTION)
{
inputFileName=jfc.getSelectedFile().getName().trim();
if(inputFileName.length()==0)
{
JOptionPane.showMessageDialog(this,"File name required");
return;
}
inputFileAbsolutePath=jfc.getSelectedFile().getAbsolutePath().trim();
if(!(inputFileAbsolutePath.toUpperCase().endsWith(".pdf")))
{
inputFileAbsolutePath=inputFileAbsolutePath+".pdf";
}
//call func that converts to pdf
try
{
employeeModel.exportToPDF(employeesTable,inputFileAbsolutePath);
JOptionPane.showMessageDialog(this, "PDF created successfully!");
}catch(ModelException me)
{
JOptionPane.showMessageDialog(this,"Failed to create PDF\nMessage : "+me.getMessage());
}

System.out.println(inputFileName+"\n"+inputFileAbsolutePath+"\nexport button clicked");//needs to be removed
}
}

}


private void addEventListeners()
{
this.employeesTable.getSelectionModel().addListSelectionListener(this);
this.addButton.addActionListener(this);
this.editButton.addActionListener(this);
this.deleteButton.addActionListener(this);
this.exportToPdfButton.addActionListener(this);
}



////////////////////////////////////////////////////////////////////////////////////////

public class AddPanel extends JPanel implements ActionListener,KeyListener
{
private JLabel titleLabel;
private JLabel empIdLabel;
private JTextField empIdTextField;
private JLabel nameLabel;
private JTextField nameTextField;
private JLabel dobLabel;
private JTextField dobTextField;
private JLabel cityLabel;
private JComboBox<String> cityComboBox;
private JLabel genderLabel;
private ButtonGroup genderGroup;
private JPanel genderPanel;
private JRadioButton maleRadioButton;
private JRadioButton femaleRadioButton;
private JLabel salaryLabel;
private JTextField salaryTextField;
private JButton saveButton;
private JButton clearButton;
private JButton cancelButton;

public AddPanel()
{
titleLabel = new JLabel("Add Employee");
empIdLabel = new JLabel("Emp ID : ");
empIdTextField = new JTextField();
nameLabel = new JLabel("Name : ");
nameTextField = new JTextField();
dobLabel = new JLabel("Date of Birth :");
dobTextField = new JTextField();
cityLabel = new JLabel("City Name : ");
cityComboBox = new JComboBox<>();
CityModel cityModel = new CityModel();
java.util.List<City> cities = cityModel.getCities();
if(cities!=null)
{
cityComboBox.addItem("<select city>");
for(City city : cities) cityComboBox.addItem(city.getName());
}
genderLabel = new JLabel("Gender : ");
maleRadioButton = new JRadioButton("Male");
femaleRadioButton = new JRadioButton("Female");
genderGroup = new ButtonGroup();
genderGroup.add(maleRadioButton);
genderGroup.add(femaleRadioButton);
genderPanel = new JPanel(new GridLayout(1,2));
genderPanel.add(maleRadioButton);
genderPanel.add(femaleRadioButton);
salaryLabel = new JLabel("Salary : ");
salaryTextField = new JTextField();
saveButton = new JButton("Save");
clearButton=new JButton("Clear");
cancelButton = new JButton("Cancel");

titleLabel.setFont(new java.awt.Font("Segoe UI",java.awt.Font.BOLD,16));
titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
empIdLabel.setHorizontalAlignment(SwingConstants.RIGHT);
nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
dobLabel.setHorizontalAlignment(SwingConstants.RIGHT);
cityLabel.setHorizontalAlignment(SwingConstants.RIGHT);
genderLabel.setHorizontalAlignment(SwingConstants.RIGHT);
salaryLabel.setHorizontalAlignment(SwingConstants.RIGHT);


//form Panel
JPanel formPanel = new JPanel(new GridLayout(6,2));
formPanel.add(empIdLabel);
formPanel.add(empIdTextField);
formPanel.add(nameLabel);
formPanel.add(nameTextField);
formPanel.add(dobLabel);
formPanel.add(dobTextField);
formPanel.add(cityLabel);
formPanel.add(cityComboBox);
formPanel.add(genderLabel);
formPanel.add(genderPanel);
formPanel.add(salaryLabel);
formPanel.add(salaryTextField);


// Buttons panel
JPanel buttonPanel = new JPanel(new GridLayout(1,7));
buttonPanel.add(new JLabel(" "));
buttonPanel.add(saveButton);
buttonPanel.add(new JLabel(" "));
buttonPanel.add(clearButton);
buttonPanel.add(new JLabel(" "));
buttonPanel.add(cancelButton);
buttonPanel.add(new JLabel(" "));


// Add panels to AddPanel
this.setLayout(new BorderLayout(0,5));
this.setBorder(BorderFactory.createEmptyBorder(0, 220, 0, 220)); 
this.add(titleLabel, BorderLayout.NORTH);
this.add(formPanel, BorderLayout.CENTER);
this.add(buttonPanel, BorderLayout.SOUTH);

saveButton.addActionListener(this);
clearButton.addActionListener(this);
cancelButton.addActionListener(this);
salaryTextField.addKeyListener(this);
}


public void actionPerformed(ActionEvent ae)
{
if(ae.getSource()==saveButton)
{
String empId = empIdTextField.getText().trim();
String name = nameTextField.getText().trim();
String dobString = dobTextField.getText().trim();  //java.sql.Date dob = java.sql.Date.valueOf(dobString);
String cityName = (String)cityComboBox.getSelectedItem(); // returns Object setSelectedItem().toString(); 
char gender = maleRadioButton.isSelected() ? 'M' : 'F';
String salaryString = salaryTextField.getText().trim();

//validate input

if(empId.length()==0)
{
JOptionPane.showMessageDialog(this,"Employee id required");
empIdTextField.requestFocus();
return;
}
if(empId.length()>10)
{
JOptionPane.showMessageDialog(this,"Length of Employee id cannot exceed 10");
empIdTextField.requestFocus();
return;
}
if(name.length()==0)
{
JOptionPane.showMessageDialog(this,"Name required");
nameTextField.requestFocus();
return;
}
if(name.length()>50)
{
JOptionPane.showMessageDialog(this,"Length of name cannot exceed 50");
nameTextField.requestFocus();
return;
}
if(dobString.length()==0)
{
JOptionPane.showMessageDialog(this,"Date required");
nameTextField.requestFocus();
return;
}
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/mm/yyyy");
java.util.Date utilDob;
java.sql.Date sqlDob;
try
{
utilDob=simpleDateFormat.parse(dobString);
sqlDob=new java.sql.Date(utilDob.getYear(),utilDob.getMonth(),utilDob.getDate());
}catch(ParseException pe)
{
JOptionPane.showMessageDialog(this,"Invalid date format. dd/mm/yyyy required");
dobTextField.setText("");
dobTextField.requestFocus();
return;
}
int dd,mm,yyyy;
String splits[]=dobString.split("/");
dd=Integer.parseInt(splits[0]);
mm=Integer.parseInt(splits[1]);
yyyy=Integer.parseInt(splits[2]);
if(mm<1 || mm>12)
{
JOptionPane.showMessageDialog(this,"Invalid date format. dd/mm/yyyy required.");
dobTextField.setText("");
dobTextField.requestFocus();
return;
}
int daysInMonth[]={31,28,31,30,31,30,31,31,30,31,30,31};
java.util.Date today=new java.util.Date();
if(yyyy<1930 || yyyy>today.getYear()+1900)
{
JOptionPane.showMessageDialog(this,"Invalid date format. dd/mm/yyyy required and year should be (1930 -"+(today.getYear()+1900)+")");
dobTextField.setText("");
dobTextField.requestFocus();
return;
}
if(yyyy%4==0 || yyyy%400==0)
{
daysInMonth[1]=29;
}
if(dd<1 || dd>daysInMonth[mm-1])
{
JOptionPane.showMessageDialog(this,"Invalid date format. dd/mm/yyyy required and day of month should be valid");
dobTextField.setText("");
dobTextField.requestFocus();
return;
}
if(cityComboBox.getSelectedIndex()==0)
{
JOptionPane.showMessageDialog(this,"Select city");
cityComboBox.requestFocus();
return;
}
if(genderGroup.getSelection()==null)
{
JOptionPane.showMessageDialog(this,"Select gender");
maleRadioButton.requestFocus();
return;
}
if(salaryString.length()==0)
{
JOptionPane.showMessageDialog(this,"Salary required");
salaryTextField.requestFocus();
return;
}
int salary;
try
{
salary=Integer.parseInt(salaryString);
}catch(NumberFormatException numberFormatException)
{
JOptionPane.showMessageDialog(this,"Invalid salary");
salaryTextField.setText("");
salaryTextField.requestFocus();
return;
}

Employee employee=new Employee();
employee.setEmpId(empId);
employee.setName(name);
employee.setDateOfBirth(sqlDob);
employee.setCityName(cityName);
employee.setGender(gender);
employee.setSalary(salary);
try
{
int addedAtIndex=employeeModel.addEmployee(employee);
employeeModel.fireTableDataChanged();
employeesTable.setEnabled(true);    
container.remove(addPanel);
container.add(southPanel, BorderLayout.SOUTH);
container.revalidate();
container.repaint();
employeesTable.setRowSelectionInterval(addedAtIndex, addedAtIndex);
employeesTable.scrollRectToVisible(employeesTable.getCellRect(addedAtIndex, 0, true));
JOptionPane.showMessageDialog(this,"Employee Added");
}catch(ModelException me)
{
JOptionPane.showMessageDialog(this,me.getMessage());
}

}
if(ae.getSource()==clearButton)
{
empIdTextField.setText("");
nameTextField.setText("");
dobTextField.setText("");
cityComboBox.setSelectedIndex(0); // or setSelectedItem("<select city>");
genderGroup.clearSelection();
salaryTextField.setText("");
empIdTextField.requestFocus();
}
if(ae.getSource()==cancelButton)
{
employeesTable.setEnabled(true);    
container.remove(addPanel);
container.add(southPanel, BorderLayout.SOUTH);
container.revalidate();
container.repaint();
}
}

public void keyPressed(KeyEvent e){}
public void keyReleased(KeyEvent e){}
public void keyTyped(KeyEvent ke)
{
char mm=ke.getKeyChar();
if(!Character.isDigit(mm))
ke.consume();
}


}//addpanel ends




/////////////////////////////////////////////////////////////////////////////
public class EditPanel extends JPanel implements ActionListener,KeyListener
{
private JLabel titleLabel;
private JLabel empIdLabel;
private JTextField empIdTextField;
private JLabel nameLabel;
private JTextField nameTextField;
private JLabel dobLabel;
private JTextField dobTextField;
private JLabel cityLabel;
private JComboBox<String> cityComboBox;
private JLabel genderLabel;
private ButtonGroup genderGroup;
private JPanel genderPanel;
private JRadioButton maleRadioButton;
private JRadioButton femaleRadioButton;
private JLabel salaryLabel;
private JTextField salaryTextField;
private JButton updateButton;
private JButton clearButton;
private JButton cancelButton;

public EditPanel()
{
titleLabel = new JLabel("Edit Employee");
empIdLabel = new JLabel("Emp ID : ");
empIdTextField = new JTextField();
nameLabel = new JLabel("Name : ");
nameTextField = new JTextField();
dobLabel = new JLabel("Date of Birth :");
dobTextField = new JTextField();
cityLabel = new JLabel("City Name : ");
cityComboBox = new JComboBox<>();
CityModel cityModel = new CityModel();
java.util.List<City> cities = cityModel.getCities();
if(cities!=null)
{
cityComboBox.addItem("<select city>");
for(City city : cities) cityComboBox.addItem(city.getName());
}
genderLabel = new JLabel("Gender : ");
maleRadioButton = new JRadioButton("Male");
femaleRadioButton = new JRadioButton("Female");
genderGroup = new ButtonGroup();
genderGroup.add(maleRadioButton);
genderGroup.add(femaleRadioButton);
genderPanel = new JPanel(new GridLayout(1,2));
genderPanel.add(maleRadioButton);
genderPanel.add(femaleRadioButton);
salaryLabel = new JLabel("Salary : ");
salaryTextField = new JTextField();
updateButton = new JButton("Update");
clearButton=new JButton("Clear");
cancelButton = new JButton("Cancel");

titleLabel.setFont(new java.awt.Font("Segoe UI",java.awt.Font.BOLD,16));
titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
empIdLabel.setHorizontalAlignment(SwingConstants.RIGHT);
nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
dobLabel.setHorizontalAlignment(SwingConstants.RIGHT);
cityLabel.setHorizontalAlignment(SwingConstants.RIGHT);
genderLabel.setHorizontalAlignment(SwingConstants.RIGHT);
salaryLabel.setHorizontalAlignment(SwingConstants.RIGHT);


//form Panel
JPanel formPanel = new JPanel(new GridLayout(6,2));
formPanel.add(empIdLabel);
formPanel.add(empIdTextField);
formPanel.add(nameLabel);
formPanel.add(nameTextField);
formPanel.add(dobLabel);
formPanel.add(dobTextField);
formPanel.add(cityLabel);
formPanel.add(cityComboBox);
formPanel.add(genderLabel);
formPanel.add(genderPanel);
formPanel.add(salaryLabel);
formPanel.add(salaryTextField);


// Buttons panel
JPanel buttonPanel = new JPanel(new GridLayout(1,7));
buttonPanel.add(new JLabel(" "));
buttonPanel.add(updateButton);
buttonPanel.add(new JLabel(" "));
buttonPanel.add(clearButton);
buttonPanel.add(new JLabel(" "));
buttonPanel.add(cancelButton);
buttonPanel.add(new JLabel(" "));


// Add panels to AddPanel
this.setLayout(new BorderLayout(0,5));
this.setBorder(BorderFactory.createEmptyBorder(0, 220, 0, 220)); 
this.add(titleLabel, BorderLayout.NORTH);
this.add(formPanel, BorderLayout.CENTER);
this.add(buttonPanel, BorderLayout.SOUTH);

this.setEmployee(employeeModel.getEmployee(employeesTable.getSelectedRow()));
empIdTextField.setEditable(false);
empIdTextField.setFocusable(false);
empIdTextField.setRequestFocusEnabled(false);
Color bgc=UIManager.getColor("TextField.background");
empIdTextField.setBackground(bgc);

updateButton.addActionListener(this);
clearButton.addActionListener(this);
cancelButton.addActionListener(this);
salaryTextField.addKeyListener(this);
}


public void actionPerformed(ActionEvent ae)
{
if(ae.getSource()==updateButton)
{
String empId = empIdTextField.getText().trim();
String name = nameTextField.getText().trim();
String dobString = dobTextField.getText().trim();  //java.sql.Date dob = java.sql.Date.valueOf(dobString);
String cityName = (String)cityComboBox.getSelectedItem(); // returns Object setSelectedItem().toString(); 
char gender = maleRadioButton.isSelected() ? 'M' : 'F';
String salaryString = salaryTextField.getText().trim();

//validate input

if(empId.length()==0)
{
JOptionPane.showMessageDialog(this,"Employee id required");
empIdTextField.requestFocus();
return;
}
if(empId.length()>10)
{
JOptionPane.showMessageDialog(this,"Length of Employee id cannot exceed 10");
empIdTextField.requestFocus();
return;
}
if(name.length()==0)
{
JOptionPane.showMessageDialog(this,"Name required");
nameTextField.requestFocus();
return;
}
if(name.length()>50)
{
JOptionPane.showMessageDialog(this,"Length of name cannot exceed 50");
nameTextField.requestFocus();
return;
}
if(dobString.length()==0)
{
JOptionPane.showMessageDialog(this,"Date required");
nameTextField.requestFocus();
return;
}
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/mm/yyyy");
java.util.Date utilDob;
java.sql.Date sqlDob;
try
{
utilDob=simpleDateFormat.parse(dobString);
sqlDob=new java.sql.Date(utilDob.getYear(),utilDob.getMonth(),utilDob.getDate());
}catch(ParseException pe)
{
JOptionPane.showMessageDialog(this,"Invalid date format. dd/mm/yyyy required");
dobTextField.setText("");
dobTextField.requestFocus();
return;
}
int dd,mm,yyyy;
String splits[]=dobString.split("/");
dd=Integer.parseInt(splits[0]);
mm=Integer.parseInt(splits[1]);
yyyy=Integer.parseInt(splits[2]);
if(mm<1 || mm>12)
{
JOptionPane.showMessageDialog(this,"Invalid date format. dd/mm/yyyy required.");
dobTextField.setText("");
dobTextField.requestFocus();
return;
}
int daysInMonth[]={31,28,31,30,31,30,31,31,30,31,30,31};
java.util.Date today=new java.util.Date();
if(yyyy<1930 || yyyy>today.getYear()+1900)
{
JOptionPane.showMessageDialog(this,"Invalid date format. dd/mm/yyyy required and year should be (1930 -"+(today.getYear()+1900)+")");
dobTextField.setText("");
dobTextField.requestFocus();
return;
}
if(yyyy%4==0 || yyyy%400==0)
{
daysInMonth[1]=29;
}
if(dd<1 || dd>daysInMonth[mm-1])
{
JOptionPane.showMessageDialog(this,"Invalid date format. dd/mm/yyyy required and day of month should be valid");
dobTextField.setText("");
dobTextField.requestFocus();
return;
}
if(cityComboBox.getSelectedIndex()==0)
{
JOptionPane.showMessageDialog(this,"Select city");
cityComboBox.requestFocus();
return;
}
if(genderGroup.getSelection()==null)
{
JOptionPane.showMessageDialog(this,"Select gender");
maleRadioButton.requestFocus();
return;
}
if(salaryString.length()==0)
{
JOptionPane.showMessageDialog(this,"Salary required");
salaryTextField.requestFocus();
return;
}
int salary;
try
{
salary=Integer.parseInt(salaryString);
}catch(NumberFormatException numberFormatException)
{
JOptionPane.showMessageDialog(this,"Invalid salary");
salaryTextField.setText("");
salaryTextField.requestFocus();
return;
}

Employee employee=new Employee();
employee.setEmpId(empId);
employee.setName(name);
employee.setDateOfBirth(sqlDob);
employee.setCityName(cityName);
employee.setGender(gender);
employee.setSalary(salary);
try
{
int updatedAtIndex=employeeModel.updateEmployee(employee);
employeeModel.fireTableDataChanged();
employeesTable.setEnabled(true);    
container.remove(editPanel);
container.add(southPanel, BorderLayout.SOUTH);
container.revalidate();
container.repaint();
JOptionPane.showMessageDialog(this,"Employee Updated");
employeesTable.setRowSelectionInterval(updatedAtIndex, updatedAtIndex);
employeesTable.scrollRectToVisible(employeesTable.getCellRect(updatedAtIndex, 0, true));
}catch(ModelException me)
{
JOptionPane.showMessageDialog(this,me.getMessage());
}

}
if(ae.getSource()==clearButton)
{
empIdTextField.setText("");
nameTextField.setText("");
dobTextField.setText("");
cityComboBox.setSelectedIndex(0); // or setSelectedItem("<select city>");
genderGroup.clearSelection();
salaryTextField.setText("");
empIdTextField.requestFocus();
}
if(ae.getSource()==cancelButton)
{
employeesTable.setEnabled(true);    
container.remove(editPanel);
container.add(southPanel, BorderLayout.SOUTH);
container.revalidate();
container.repaint();
}
}

public void keyPressed(KeyEvent e){}
public void keyReleased(KeyEvent e){}
public void keyTyped(KeyEvent ke)
{
char mm=ke.getKeyChar();
if(!Character.isDigit(mm))
ke.consume();
}

public void setEmployee(Employee employee)
{
empIdTextField.setText(employee.getEmpId().trim());
nameTextField.setText(employee.getName().trim());
dobTextField.setText(String.valueOf(employee.getDateOfBirth()));
cityComboBox.setSelectedItem(employee.getCityName().trim());
if(employee.getGender()=='M') maleRadioButton.setSelected(true);
else femaleRadioButton.setSelected(true);
salaryTextField.setText(String.valueOf(employee.getSalary()));
}

}


///////////////////////////////////////////////////////////////////
class DeletePanel extends JPanel
{
private JButton deleteButton;
private JButton cancelButton;
private JLabel confirmationLabel;
private JDialog dialog;
public DeletePanel()
{
confirmationLabel=new JLabel("Are you sure want to delete the employee?",SwingConstants.CENTER);
//confirmationLabel.setFont(new Font("",Font.BOLD,16));
deleteButton=new JButton("Delete");
cancelButton=new JButton("Cancel");
JPanel buttonPanel=new JPanel(new GridLayout(1,5));
buttonPanel.add(new JLabel(" "));
buttonPanel.add(deleteButton);
buttonPanel.add(new JLabel(" "));
buttonPanel.add(cancelButton);
buttonPanel.add(new JLabel(" "));
dialog = new JDialog(EmployeeCRUDUI.this, "Delete Employee", true);
dialog.setSize(390, 150);
dialog.setLocationRelativeTo(EmployeeCRUDUI.this);
dialog.setLayout(new BorderLayout());
dialog.add(confirmationLabel,BorderLayout.CENTER);
dialog.add(buttonPanel,BorderLayout.SOUTH);        
this.deleteButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ae)
{
try
{
employeeModel.deleteEmployee(employeeModel.getEmployee(employeesTable.getSelectedRow()));
}catch(ModelException modelException)
{
JOptionPane.showMessageDialog(DeletePanel.this,modelException.getMessage());
return;
}
employeeModel.fireTableDataChanged();
dialog.dispose();
container.revalidate();
container.repaint();
JOptionPane.showMessageDialog(EmployeeCRUDUI.this,"Employee deleted");
employeeDetailPanel.clear();
}
});
this.cancelButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ae)
{
dialog.dispose();
}
});
dialog.setVisible(true);
}
}//deletepanel ends




}//employeeCRUDUI ends






class DBConfig
{
public static String driver;
public static String serverUrl;
public static String dbName;
public static String url;
public static String user;
public static String password;

public static void init()
{
RandomAccessFile raf=null;
try
{
raf = new RandomAccessFile("db.properties", "r");
Properties properties = new Properties();
properties.load(new FileInputStream(raf.getFD()));
driver = properties.getProperty("db.driver");
serverUrl = properties.getProperty("db.serverUrl");
dbName = properties.getProperty("db.name");
url = properties.getProperty("db.url");
user = properties.getProperty("db.user");
password = properties.getProperty("db.password");
Class.forName(driver);
try{ if(raf!=null) raf.close(); }catch(Exception ex){}
}
catch(IOException | ClassNotFoundException e)
{ 
JOptionPane.showMessageDialog(null,"Failed to connect to the database.\n\n" +"Please make sure 'db.properties' exists and is correctly configured.\n\n" +"Details: " + e.getMessage(),"Database Configuration Error",JOptionPane.ERROR_MESSAGE);
try{ if(raf!=null) raf.close(); }catch(Exception ex){}
System.exit(0);
}
}
}



class DatabaseInitializer
{
public static void initializeIfNeeded()
{
try
{
Connection connection = DriverManager.getConnection(DBConfig.serverUrl, DBConfig.user, DBConfig.password);
Statement statement = connection.createStatement();

// Is the database already set up?
ResultSet resultSet = statement.executeQuery(
"SELECT SCHEMA_NAME FROM information_schema.SCHEMATA WHERE SCHEMA_NAME = '" + DBConfig.dbName + "'");
boolean alreadySetup = resultSet.next();
resultSet.close();

if(!alreadySetup)
{
// First run: create the database and its tables (schema only, no data)
statement.execute("CREATE DATABASE IF NOT EXISTS `" + DBConfig.dbName + "`");
statement.execute("USE `" + DBConfig.dbName + "`");

StringBuilder script = new StringBuilder();
BufferedReader reader = new BufferedReader(new FileReader("database/schema.sql"));
String line;
while((line = reader.readLine()) != null)
{
String trimmed = line.trim();
if(trimmed.isEmpty() || trimmed.startsWith("--") || trimmed.startsWith("/*")) continue;
script.append(line).append("\n");
}
reader.close();

statement.execute("SET FOREIGN_KEY_CHECKS=0");
for(String sql : script.toString().split(";"))
{
if(sql.trim().isEmpty()) continue;
statement.execute(sql);
}
statement.execute("SET FOREIGN_KEY_CHECKS=1");
}
statement.close();
connection.close();
}
catch(Exception e)
{
JOptionPane.showMessageDialog(null,
"Failed to set up the database on first run.\n\n" +
"Make sure MySQL is running and the user in db.properties\n" +
"has permission to create databases.\n\nDetails: " + e.getMessage(),
"Database Setup Error", JOptionPane.ERROR_MESSAGE);
System.exit(0);
}
}
}


class EmployeeCRUD
{
public static void main(String args[])
{
DBConfig.init();
DatabaseInitializer.initializeIfNeeded();
EmployeeCRUDUI e=new EmployeeCRUDUI();
}
}
