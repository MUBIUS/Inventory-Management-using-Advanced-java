import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.sql.*;
class InventoryManagement extends JFrame 
{
	JTabbedPane jtp;
	public InventoryManagement()
	{
		setSize(500,500);
		setLayout(new BorderLayout());
		jtp=new JTabbedPane();
		jtp.addTab("Stock Add",new StockAddPanel());
		jtp.addTab("Stock Delete",new StockDeletePanel());
		add(jtp,BorderLayout.CENTER);
		
		setVisible(true);
	}
	public static void main(String abc[])
	{
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Connection connect=DriverManager.getConnection("jdbc:odbc:MubasheerDS");
			Statement statement;
			InventoryManagement project=new InventoryManagement();
			project.setDefaultCloseOperation(EXIT_ON_CLOSE);
			connect.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}
class StockAddPanel extends JPanel implements ActionListener
{
	AddStockPanel addpanel=new AddStockPanel();
	JLabel ID,name,quantity,price,stock;
	JButton addStock;
	int y=0;
	String s1,s2,s3,s4;
	static JDialog d;
	StockAddPanel()
	{
		GridBagLayout gb=new GridBagLayout();
		GridBagConstraints gbc=new GridBagConstraints();
		setLayout(gb);

		ID=new JLabel("ID");
		name=new JLabel("Name");
		quantity=new JLabel("Quantity");
		price=new JLabel("Price");
		stock=new JLabel("Stock");
		addStock=new JButton("Add Stock");

		gbc.fill=GridBagConstraints.HORIZONTAL;
		gbc.ipadx=40;
		gbc.ipady=20;
		gbc.gridx=0;
		gbc.gridy=0;
		add(ID,gbc);

		gbc.gridx=1;
		gbc.gridy=0;
		add(name,gbc);

		gbc.gridx=2;
		gbc.gridy=0;
		add(quantity,gbc);

		gbc.gridx=3;
		gbc.gridy=0;
		add(price,gbc);

		gbc.gridx=4;
		gbc.gridy=0;
		add(stock,gbc);

		try
		{
			String s="select * from Table1";
			Connection connect=DriverManager.getConnection("jdbc:odbc:MubasheerDS");
			Statement statement=connect.createStatement();
			ResultSet rs=statement.executeQuery(s);
			while(rs.next())
			{
				y++;
				gbc.fill=GridBagConstraints.HORIZONTAL;
				gbc.ipadx=40;
				gbc.ipady=20;
				gbc.gridx=0;
				gbc.gridy=y;
				add(new JLabel(s1=Integer.toString(rs.getInt("ID"))),gbc);

				gbc.gridx=1;
				gbc.gridy=y;
				add(new JLabel(rs.getString("StockName")),gbc);

				gbc.gridx=2;
				gbc.gridy=y;
				add(new JLabel(s2=Integer.toString(rs.getInt("Quantity"))),gbc);

				gbc.gridx=3;
				gbc.gridy=y;
				add(new JLabel(s3=Integer.toString(rs.getInt("Price"))),gbc);

				gbc.gridx=4;
				gbc.gridy=y;
				add(new JLabel(s4=Integer.toString(rs.getInt("InStock"))),gbc);
				

			}

			connect.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}

		
		gbc.fill=GridBagConstraints.HORIZONTAL;
		gbc.ipady=20;
		gbc.gridx=100;
		gbc.gridy=100;
		add(addStock,gbc);
		addStock.addActionListener(this);

	}
	public void actionPerformed(ActionEvent ae)
	{
		addpanel.addPage();
	}
}
class AddStockPanel extends JFrame implements ActionListener
{
	JLabel IDlabel,namelabel,quantitylabel,pricelabel,stocklabel;
	JTextField IDfield,namefield,quantityfield,pricefield,stockfield;
	JButton submit;
	PreparedStatement statement;
	static JDialog d;

	void addPage()
	{
		
		setSize(500,500);
		GridBagLayout gb=new GridBagLayout();
		GridBagConstraints gbc=new GridBagConstraints();

		setLayout(gb);

		IDlabel=new JLabel("Enter ID of the product");
		namelabel=new JLabel("Enter Name of the product");
		quantitylabel=new JLabel("Enter Quantity of the product");
		pricelabel=new JLabel("Enter price of the product");
		stocklabel=new JLabel("Enter available stock");
		
		IDfield=new JTextField(15);
		namefield=new JTextField(15);
		quantityfield=new JTextField(15);
		pricefield=new JTextField(15);
		stockfield=new JTextField(15);

		submit=new JButton("SUBMIT");
		submit.addActionListener(this);

		gbc.fill=GridBagConstraints.HORIZONTAL;
		gbc.ipady=20;
		gbc.gridx=0;
		gbc.gridy=0;
		add(IDlabel,gbc);

		gbc.gridx=1;
		gbc.gridy=0;
		add(IDfield,gbc);

		gbc.fill=GridBagConstraints.HORIZONTAL;
		gbc.gridx=0;
		gbc.gridy=1;
		add(namelabel,gbc);

		gbc.gridx=1;
		gbc.gridy=1;
		add(namefield,gbc);

		gbc.fill=GridBagConstraints.HORIZONTAL;
		gbc.gridx=0;
		gbc.gridy=2;
		add(quantitylabel,gbc);

		gbc.gridx=1;
		gbc.gridy=2;
		add(quantityfield,gbc);

		gbc.fill=GridBagConstraints.HORIZONTAL;
		gbc.gridx=0;
		gbc.gridy=3;
		add(pricelabel,gbc);

		gbc.gridx=1;
		gbc.gridy=3;
		add(pricefield,gbc);

		gbc.fill=GridBagConstraints.HORIZONTAL;
		gbc.gridx=0;
		gbc.gridy=4;
		add(stocklabel,gbc);

		gbc.gridx=1;
		gbc.gridy=4;
		add(stockfield,gbc);

		gbc.fill=GridBagConstraints.HORIZONTAL;
		gbc.gridx=0;
		gbc.gridy=5;
		gbc.gridwidth=2;
		add(submit,gbc);

		setVisible(true);
	}
	public void actionPerformed(ActionEvent ae)
	{
		try
		{
			Connection connect=DriverManager.getConnection("jdbc:odbc:MubasheerDS");
			String s="Insert into Table1(ID,StockName,Quantity,Price,InStock) values(?,?,?,?,?)";
			statement=connect.prepareStatement(s);

			statement.setInt(1,Integer.parseInt(IDfield.getText()));
			statement.setString(2,namefield.getText().toString());
			statement.setInt(3,Integer.parseInt(quantityfield.getText()));
			statement.setInt(4,Integer.parseInt(pricefield.getText()));
			statement.setInt(5,Integer.parseInt(stockfield.getText()));

			statement.executeUpdate();
			
			connect.close();
		}
		catch(Exception e)
		{
			d=new JDialog(new AddStockPanel(),"Error",true);
			d.setLayout(new BorderLayout());  
       			JButton b = new JButton ("OK");  
        			b.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)  
				{
					AddStockPanel.d.setVisible(false);
				}
			});
			d.add(new JLabel(""+e),BorderLayout.CENTER);
			d.add(b,BorderLayout.SOUTH);
			d.setSize(500,500);
			d.setVisible(true);
		}

	}
}
class StockDeletePanel extends JPanel implements ActionListener
{
	JLabel ID,name,quantity,price,stock;
	JButton deleteStock;
	int y=0;
	String s1,s2,s3,s4;
	DeletePanel deletepanel=new DeletePanel();
	StockDeletePanel()
	{
		GridBagLayout gb=new GridBagLayout();
		GridBagConstraints gbc=new GridBagConstraints();
		setLayout(gb);

		ID=new JLabel("ID");
		name=new JLabel("Name");
		quantity=new JLabel("Quantity");
		price=new JLabel("Price");
		stock=new JLabel("Stock");
		deleteStock=new JButton("Delete Stock");

		gbc.fill=GridBagConstraints.HORIZONTAL;
		gbc.ipadx=40;
		gbc.ipady=20;
		gbc.gridx=0;
		gbc.gridy=0;
		add(ID,gbc);

		gbc.gridx=1;
		gbc.gridy=0;
		add(name,gbc);

		gbc.gridx=2;
		gbc.gridy=0;
		add(quantity,gbc);

		gbc.gridx=3;
		gbc.gridy=0;
		add(price,gbc);

		gbc.gridx=4;
		gbc.gridy=0;
		add(stock,gbc);

		try
		{
			String s="select * from Table1";
			Connection connect=DriverManager.getConnection("jdbc:odbc:MubasheerDS");
			Statement statement=connect.createStatement();
			ResultSet rs=statement.executeQuery(s);
			while(rs.next())
			{
				y++;
				gbc.fill=GridBagConstraints.HORIZONTAL;
				gbc.ipadx=40;
				gbc.ipady=20;
				gbc.gridx=0;
				gbc.gridy=y;
				add(new JLabel(s1=Integer.toString(rs.getInt("ID"))),gbc);

				gbc.gridx=1;
				gbc.gridy=y;
				add(new JLabel(rs.getString("StockName")),gbc);

				gbc.gridx=2;
				gbc.gridy=y;
				add(new JLabel(s2=Integer.toString(rs.getInt("Quantity"))),gbc);

				gbc.gridx=3;
				gbc.gridy=y;
				add(new JLabel(s3=Integer.toString(rs.getInt("Price"))),gbc);

				gbc.gridx=4;
				gbc.gridy=y;
				add(new JLabel(s4=Integer.toString(rs.getInt("InStock"))),gbc);
				

			}
			
			connect.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}

		
		gbc.fill=GridBagConstraints.HORIZONTAL;
		gbc.ipady=20;
		gbc.gridx=100;
		gbc.gridy=100;
		add(deleteStock,gbc);
		deleteStock.addActionListener(this);

	}
	public void actionPerformed(ActionEvent ae)
	{
		deletepanel.deletePage();
	}
}
class DeletePanel extends JFrame implements ActionListener
{
	JLabel label1;
	JTextField textfield1;
	JButton submit;

	static JDialog d;

	PreparedStatement statement;
	void deletePage()
	{
		setSize(500,500);
		GridBagLayout gb=new GridBagLayout();
		GridBagConstraints gbc=new GridBagConstraints();

		setLayout(gb);

		label1=new JLabel("Enter ID of the product you want to delete");
		textfield1=new JTextField(15);
		submit=new JButton("Submit");

		gbc.fill=GridBagConstraints.HORIZONTAL;
		gbc.ipadx=20;
		gbc.ipady=20;
		gbc.gridx=0;
		gbc.gridy=0;
		add(label1,gbc);

		gbc.gridx=1;
		gbc.gridy=0;
		add(textfield1,gbc);

		gbc.fill=GridBagConstraints.HORIZONTAL;
		gbc.ipadx=20;
		gbc.ipady=20;
		gbc.gridx=0;
		gbc.gridy=2;
		gbc.gridwidth=2;
		add(submit,gbc);

		submit.addActionListener(this);

		setVisible(true);

	}
	public void actionPerformed(ActionEvent ae)
	{
		try
		{
			Connection connect=DriverManager.getConnection("jdbc:odbc:MubasheerDS");

			String deleteID=textfield1.getText().toString();

			statement=connect.prepareStatement("delete from Table1 where ID="+deleteID);

			statement.executeUpdate();
			connect.close();
		}
		catch(Exception e)
		{
			d=new JDialog(new DeletePanel(),"Error",true);
			d.setLayout(new BorderLayout());  
       			JButton b = new JButton ("OK");  
        			b.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)  
				{
					DeletePanel.d.setVisible(false);
				}
			});
			d.add(new JLabel(""+e),BorderLayout.CENTER);
			d.add(b,BorderLayout.SOUTH);
			d.setSize(500,500);
			d.setVisible(true);
		}

	
	}
}