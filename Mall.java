import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;


class WindowCloser extends WindowAdapter{
	public void windowClosing(WindowEvent w){
		Object x=w.getSource();
		Frame f;
		if(x instanceof Frame){f=(Frame)x;
		f.setVisible(false);}
		System.exit(0);

	}
	}
class Logout implements ActionListener{
	public void actionPerformed(ActionEvent e){
		Mall.emp_frame.setVisible(false);
		Mall.adm_frame.setVisible(false);
		Mall.main_frame.setVisible(true);
		Mall.card.show(Mall.emp_frame,"emp_home_panel");
	}
	}
class Mall{
	static String currm="";
	static String error="";
	static Dialog  dia,cdia;	
	static Connection con;		
	static Statement stmt,stmt1;
	static PreparedStatement ps,ps1;
	static ResultSet rs,rs1;
	static Frame main_frame=new Frame("Welcome");
	static Frame emp_frame=new Frame("Employee");
	static Frame adm_frame=new Frame("Admin");
	static CardLayout card=new CardLayout();
	static TextField user_tf,pass_tf;
	static String userId;
	static long millis=System.currentTimeMillis();  
    static java.sql.Date date=new java.sql.Date(millis);
    static Vector<Vector<String>> vector2;
    static Vector<String> vector;  

    public static String pd(String s, int n) {
     		return String.format("%1$-" + n + "s", s);  
				}
	public static String pl(String s, int n) {
     		return String.format("%1$" + n + "s", s);  
				}
	public static void main(String[] args)throws Exception {
				
		Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection("jdbc:mysql://localhost/Supermarket","root","root");
		
		stmt=con.createStatement();
		stmt1=con.createStatement(); 
		
		main_frame.setSize(600,300);
		emp_frame.setSize(600,300);
		adm_frame.setSize(600,300);
		main_frame.setVisible(true);

		//main_frame.setBackground(Color.WHITE);
		
		main_frame.setLayout(new GridBagLayout());
		CardLayout emp_card=new CardLayout();
		CardLayout adm_card=new CardLayout();

		emp_frame.setLayout(card);
		adm_frame.setLayout(card);
		
		//rs1=stmt1.executeQuery("select p.SuppID from _ORDER  o , PRODUCT p where  o.ProdID=p.ProdID ");
		//while(rs1.next())System.out.print((rs1.getString(1)));
		
 ////////////////////////////login  start//////////////
		CheckboxGroup cbgm=new CheckboxGroup();
		Checkbox admincb=new Checkbox("Admin",false,cbgm);
		Checkbox empcb=new Checkbox("Employee",false,cbgm);
		admincb.setFont(new Font("Serif",Font.BOLD,15));
		empcb.setFont(new Font("Serif",Font.BOLD,15));

		Label user_label=new Label("UserName");
		Label pass_label=new Label("password");
		Label login_label=new Label(" Welcome to Triton Mall ");
		login_label.setFont(new Font("Serif",Font.BOLD,18));

		pass_label.setFont(new Font("Serif",Font.BOLD,15));
		user_label.setFont(new Font("Serif",Font.BOLD,15));

		user_tf=new TextField(30);
		pass_tf=new TextField(30);
		pass_tf.setEchoChar('*');

		Button login_button=new Button("Login");
		login_button.setFont(new Font("Serif",Font.BOLD,16));

		GridBagConstraints gbc=new GridBagConstraints();
		gbc.gridx=0;gbc.gridy=0;gbc.gridwidth=2;gbc.gridheight=1;gbc.anchor=GridBagConstraints.CENTER;
		gbc.weightx=8;gbc.weighty=1;
		main_frame.add(login_label,gbc);
		gbc.gridy=1;gbc.weightx=4;gbc.gridwidth=1;
		main_frame.add(user_label,gbc);
		gbc.gridx=1;
		main_frame.add(user_tf,gbc);
		gbc.gridy=2;gbc.gridx=0;
		main_frame.add(pass_label,gbc);
		gbc.gridx=1;
		main_frame.add(pass_tf,gbc);
		gbc.gridy=3;gbc.gridx=0;
		main_frame.add(admincb,gbc);
		gbc.gridx=1;
		main_frame.add(empcb,gbc);
		gbc.gridy=4;gbc.gridx=0;
		gbc.weightx=8;gbc.gridwidth=2;
		main_frame.add(login_button,gbc);

		Button bb=new Button("OK");
		bb.setSize(100,100);
		bb.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){dia.setVisible(false);}});

		login_button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e ){
				try{
				dia=new Dialog(main_frame,"Attention !",true);
				dia.setLayout(new GridBagLayout());
				dia.setSize(300,150);
				dia.setLayout(new GridLayout(2,1));
				dia.add(new Label("Wrong password or Access"));
				dia.add(bb);
				rs=stmt.executeQuery("select  Password from USERS where UserID = '"+user_tf.getText()+"' ");
				rs1=stmt1.executeQuery("select Type from USERS where UserID = '"+user_tf.getText()+"' ");
				while (rs.next() && rs1.next())
				if (pass_tf.getText().equals(rs.getString(1))&& cbgm.getSelectedCheckbox().getLabel().equals(rs1.getString(1))) 
					{System.out.println("Passed");
						main_frame.setVisible(false);
						if(cbgm.getSelectedCheckbox().getLabel().equals("Employee"))
						emp_frame.setVisible(true);
						else adm_frame.setVisible(true);
						//user_tf.setText("");
						pass_tf.setText("");
						userId=user_tf.getText();
						System.out.print(userId);
						}
				else {dia.setVisible(true);}
				}catch (Exception ex){ex.printStackTrace();}
			}


		});
		main_frame.addWindowListener(new WindowCloser());

 ///////////admin frame start //////

		MenuBar adm_menuBar =new MenuBar();
		Menu adm_new=new Menu("New");
		Menu adm_edit=new Menu("Edit");
		Menu adm_delete=new Menu("Delete");
		Menu adm_options=new Menu("More");
		MenuItem adm_account=new MenuItem("Account");      
		MenuItem adm_logout=new MenuItem("Logout");

		MenuItem adm_new_emp=new MenuItem("Employee");
		MenuItem adm_new_supplier=new MenuItem("Supplier");
		MenuItem adm_new_prod=new MenuItem("Product");
		MenuItem adm_new_deptt=new MenuItem("Department");
		MenuItem adm_edit_emp=new MenuItem("Employee");
		MenuItem adm_edit_prod=new MenuItem("Product");
		MenuItem adm_edit_deptt=new MenuItem("Department");
		MenuItem adm_delete_emp=new MenuItem("Employee");
		MenuItem adm_delete_deptt=new MenuItem("Department");
		MenuItem adm_delete_supplier=new MenuItem("Supplier");
		MenuItem adm_delete_prod=new MenuItem("Product");


		adm_menuBar.setFont(new Font("Serif",Font.BOLD,16));

		Menu adm_view=new Menu("View");
		MenuItem adm_view_sales=new MenuItem("Sales");
		MenuItem adm_view_prod=new MenuItem("Products");
		MenuItem adm_view_deptt=new MenuItem("Department");
		MenuItem adm_view_emp=new MenuItem("Employees");
		MenuItem adm_view_order=new MenuItem("Orders");
		//MenuItem adm_view_user=new MenuItem("Users");
		MenuItem adm_view_revenue=new MenuItem("Revenue");
		MenuItem adm_view_supplier=new MenuItem("Suppliers");
		adm_view.add(adm_view_sales);
		adm_view.add(adm_view_deptt);
		adm_view.add(adm_view_prod);
		adm_view.add(adm_view_order);
		adm_view.add(adm_view_supplier);
		//adm_view.add(adm_view_revenue);
		adm_view.add(adm_view_emp);
		//adm_view.add(adm_view_user);

		adm_menuBar.add(adm_new);
		adm_menuBar.add(adm_edit);
		adm_menuBar.add(adm_delete);
		adm_menuBar.add(adm_view);
		adm_menuBar.add(adm_options);

		adm_new.add(adm_new_emp);adm_new.add(adm_new_deptt);adm_new.add(adm_new_prod);adm_new.add(adm_new_supplier);
		adm_edit.add(adm_edit_emp);adm_edit.add(adm_edit_deptt);adm_edit.add(adm_edit_prod);
		adm_delete.add(adm_delete_emp);adm_delete.add(adm_delete_deptt);
		adm_delete.add(adm_delete_prod);adm_delete.add(adm_delete_supplier);

		adm_options.add(adm_account);
		adm_options.add(adm_logout);



		adm_frame.setMenuBar(adm_menuBar);
		adm_frame.addWindowListener(new WindowCloser());
		adm_logout.addActionListener(new Logout());

 ///////////panles declaration////////////
		Panel adm_home_panel=new Panel(new GridBagLayout());
		Panel adm_account_panel=new Panel(new GridBagLayout());
		Panel adm_new_supplier_panel=new Panel(new GridBagLayout());
		Panel adm_new_prod_panel=new Panel(new GridBagLayout());
		Panel adm_new_deptt_panel=new Panel(new GridBagLayout());
		Panel adm_new_emp_panel=new Panel(new GridBagLayout());
		Panel adm_edit_prod_panel=new Panel(new GridBagLayout());
		Panel adm_edit_deptt_panel=new Panel(new GridBagLayout());
		Panel adm_edit_emp_panel=new Panel(new GridBagLayout());
		Panel adm_delete_supplier_panel=new Panel(new GridBagLayout());
		Panel adm_delete_emp_panel=new Panel(new GridBagLayout());
		Panel adm_delete_prod_panel=new Panel(new GridBagLayout());
		Panel adm_delete_deptt_panel=new Panel(new GridBagLayout());
		//Panel adm_view_user_panel=new Panel(new GridBagLayout());
		Panel adm_view_supplier_panel=new Panel(new GridBagLayout());
		//Panel adm_view_revenue_panel=new Panel(new GridBagLayout());
		Panel adm_view_emp_panel=new Panel(new GridBagLayout());
		Panel adm_view_order_panel=new Panel(new GridBagLayout());
		Panel adm_view_deptt_panel=new Panel(new GridBagLayout());
		Panel adm_view_prod_panel=new Panel(new GridBagLayout());
		Panel adm_view_sales_panel=new Panel(new GridBagLayout());
		adm_home_panel.add(new Label("Admin adm_home_panel"));

 /////////////adding them to frame//////////////
		adm_frame.add(adm_home_panel,"adm_home_panel");
		adm_frame.add(adm_account_panel,"adm_account_panel");
		adm_frame.add(adm_new_emp_panel,"adm_new_emp_panel");
		adm_frame.add(adm_new_supplier_panel,"adm_new_supplier_panel");
		adm_frame.add(adm_new_prod_panel,"adm_new_prod_panel");
		adm_frame.add(adm_new_deptt_panel,"adm_new_deptt_panel");
		adm_frame.add(adm_edit_emp_panel,"adm_edit_emp_panel");
		adm_frame.add(adm_edit_deptt_panel,"adm_edit_deptt_panel");
		adm_frame.add(adm_edit_prod_panel,"adm_edit_prod_panel");
		adm_frame.add(adm_delete_emp_panel,"adm_delete_emp_panel");
		adm_frame.add(adm_delete_deptt_panel,"adm_delete_deptt_panel");
		adm_frame.add(adm_delete_supplier_panel,"adm_delete_supplier_panel");
		adm_frame.add(adm_delete_prod_panel,"adm_delete_prod_panel");
		adm_frame.add(adm_view_supplier_panel,"adm_view_supplier_panel");
		adm_frame.add(adm_view_prod_panel,"adm_view_prod_panel");
		//adm_frame.add(adm_view_user_panel,"adm_view_user_panel");
		//adm_frame.add(adm_view_revenue_panel,"adm_view_revenue_panel");
		adm_frame.add(adm_view_emp_panel,"adm_view_emp_panel");
		adm_frame.add(adm_view_order_panel,"adm_view_order_panel");
		adm_frame.add(adm_view_deptt_panel,"adm_view_deptt_panel");
		adm_frame.add(adm_view_sales_panel,"adm_view_sales_panel");

 ////////////////adding actionListeners to menu items of Admin frame//////////
		adm_account.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(adm_account_panel.getComponentCount()==0)
					setupAdm_account_panel(adm_account_panel);
				card.show(adm_frame,"adm_account_panel");
				}});
		adm_new_emp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(adm_new_emp_panel.getComponentCount()==0)
					setupAdm_new_emp_panel(adm_new_emp_panel);
				else{TextField t;
					for(Component c:adm_new_emp_panel.getComponents()){
						if(c instanceof TextField){t=(TextField)c;t.setText("");}
						}}
				card.show(adm_frame,"adm_new_emp_panel");
			}});
		adm_new_prod.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){if(adm_new_prod_panel.getComponentCount()==0)
					setupAdm_new_prod_panel(adm_new_prod_panel);
				else{TextField t;
					for(Component c:adm_new_prod_panel.getComponents()){
						if(c instanceof TextField){t=(TextField)c;t.setText("");}
						}}
				card.show(adm_frame,"adm_new_prod_panel");}});
		adm_new_deptt.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){if(adm_new_deptt_panel.getComponentCount()==0)
					setupAdm_new_deptt_panel(adm_new_deptt_panel);
				else{TextField t;
					for(Component c:adm_new_deptt_panel.getComponents()){
						if(c instanceof TextField){t=(TextField)c;t.setText("");}
						}}
				card.show(adm_frame,"adm_new_deptt_panel");}});
		adm_new_supplier.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){if(adm_new_supplier_panel.getComponentCount()==0)
					setupAdm_new_supplier_panel(adm_new_supplier_panel);
				else{TextField t;
					for(Component c:adm_new_supplier_panel.getComponents()){
						if(c instanceof TextField){t=(TextField)c;t.setText("");}
						}}
				card.show(adm_frame,"adm_new_supplier_panel");}});
		adm_edit_prod.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){if(adm_edit_prod_panel.getComponentCount()==0)
					setupAdm_edit_prod_panel(adm_edit_prod_panel);
				else{TextField t;
					for(Component c:adm_edit_prod_panel.getComponents()){
						if(c instanceof TextField){t=(TextField)c;t.setText("");}
						}}
				card.show(adm_frame,"adm_edit_prod_panel");}});
		adm_edit_deptt.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){if(adm_edit_deptt_panel.getComponentCount()==0)
					setupAdm_edit_deptt_panel(adm_edit_deptt_panel);
				else{TextField t;
					for(Component c:adm_edit_deptt_panel.getComponents()){
						if(c instanceof TextField){t=(TextField)c;t.setText("");}
						}}
				card.show(adm_frame,"adm_edit_deptt_panel");}});
		adm_edit_emp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){if(adm_edit_emp_panel.getComponentCount()==0)
					setupAdm_edit_emp_panel(adm_edit_emp_panel);
				else{TextField t;
					for(Component c:adm_edit_emp_panel.getComponents()){
						if(c instanceof TextField){t=(TextField)c;t.setText("");}
						}}
				card.show(adm_frame,"adm_edit_emp_panel");}});
		adm_delete_supplier.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){if(adm_delete_supplier_panel.getComponentCount()==0)
					setupAdm_delete_supplier_panel(adm_delete_supplier_panel);
				else{TextField t;
					for(Component c:adm_delete_supplier_panel.getComponents()){
						if(c instanceof TextField){t=(TextField)c;t.setText("");}
						}}
				card.show(adm_frame,"adm_delete_supplier_panel");}});
		adm_delete_emp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){if(adm_delete_emp_panel.getComponentCount()==0)
					setupAdm_delete_emp_panel(adm_delete_emp_panel);
				else{TextField t;
					for(Component c:adm_delete_emp_panel.getComponents()){
						if(c instanceof TextField){t=(TextField)c;t.setText("");}
						}}
				card.show(adm_frame,"adm_delete_emp_panel");}});
		adm_delete_prod.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){if(adm_delete_prod_panel.getComponentCount()==0)
					setupAdm_delete_prod_panel(adm_delete_prod_panel);
				else{TextField t;
					for(Component c:adm_delete_prod_panel.getComponents()){
						if(c instanceof TextField){t=(TextField)c;t.setText("");}
						}}
				card.show(adm_frame,"adm_delete_prod_panel");}});
		adm_delete_deptt.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){if(adm_delete_deptt_panel.getComponentCount()==0)
					setupAdm_delete_deptt_panel(adm_delete_deptt_panel);
				else{TextField t;
					for(Component c:adm_delete_deptt_panel.getComponents()){
						if(c instanceof TextField){t=(TextField)c;t.setText("");}
						}}
				card.show(adm_frame,"adm_delete_deptt_panel");}});
		/*adm_view_user.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){if(adm_view_user_panel.getComponentCount()==0)
					setupAdm_view_user_panel(adm_view_user_panel);
				else{TextField t;
					TextArea t1;
					for(Component c:adm_view_user_panel.getComponents()){
						if(c instanceof TextField ){t=(TextField)c;t.setText("");}
						else if(c instanceof TextArea){t1=(TextArea)c;t1.setText("");}
							}}
				card.show(adm_frame,"adm_view_user_panel");}});*/
		adm_view_supplier.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){if(adm_view_supplier_panel.getComponentCount()==0)
					setupAdm_view_supplier_panel(adm_view_supplier_panel);
				else{TextField t;
					TextArea t1;
					for(Component c:adm_view_supplier_panel.getComponents()){
						if(c instanceof TextField ){t=(TextField)c;t.setText("");}
						else if(c instanceof TextArea){t1=(TextArea)c;t1.setText("");}
							}}
				card.show(adm_frame,"adm_view_supplier_panel");}});
		/*adm_view_revenue.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){if(adm_view_revenue_panel.getComponentCount()==0)
					setupAdm_view_revenue_panel(adm_view_revenue_panel);
				else{TextField t;
					TextArea t1;
					for(Component c:adm_view_revenue_panel.getComponents()){
						if(c instanceof TextField ){t=(TextField)c;t.setText("");}
						else if(c instanceof TextArea){t1=(TextArea)c;t1.setText("");}
							}}
				card.show(adm_frame,"adm_view_revenue_panel");}});*/
		adm_view_emp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){if(adm_view_emp_panel.getComponentCount()==0)
					setupAdm_view_emp_panel(adm_view_emp_panel);
				else{TextField t;
					TextArea t1;
					for(Component c:adm_view_emp_panel.getComponents()){
						if(c instanceof TextField ){t=(TextField)c;t.setText("");}
						else if(c instanceof TextArea){t1=(TextArea)c;t1.setText("");}
							}}
				card.show(adm_frame,"adm_view_emp_panel");}});
		adm_view_order.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){if(adm_view_order_panel.getComponentCount()==0)
					setupAdm_view_order_panel(adm_view_order_panel);
				else{TextField t;
					TextArea t1;
					for(Component c:adm_view_order_panel.getComponents()){
						if(c instanceof TextField ){t=(TextField)c;t.setText("");}
						else if(c instanceof TextArea){t1=(TextArea)c;t1.setText("");}
							}}
				card.show(adm_frame,"adm_view_order_panel");}});
		adm_view_deptt.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){if(adm_view_deptt_panel.getComponentCount()==0)
					setupAdm_view_deptt_panel(adm_view_deptt_panel);
				else{TextField t;
					TextArea t1;
					for(Component c:adm_view_deptt_panel.getComponents()){
						if(c instanceof TextField ){t=(TextField)c;t.setText("");}
						else if(c instanceof TextArea){t1=(TextArea)c;t1.setText("");}
							}}
				card.show(adm_frame,"adm_view_deptt_panel");}});
		adm_view_prod.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){if(adm_view_prod_panel.getComponentCount()==0)
					setupAdm_view_prod_panel(adm_view_prod_panel);
				else{TextField t;
					TextArea t1;
					for(Component c:adm_view_prod_panel.getComponents()){
						if(c instanceof TextField ){t=(TextField)c;t.setText("");}
						else if(c instanceof TextArea){t1=(TextArea)c;t1.setText("");}
							}}
				card.show(adm_frame,"adm_view_prod_panel");}});
		adm_view_sales.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){if(adm_view_sales_panel.getComponentCount()==0)
					setupAdm_view_sales_panel(adm_view_sales_panel);
				else{TextField t;
					TextArea t1;
					for(Component c:adm_view_sales_panel.getComponents()){
						if(c instanceof TextField ){t=(TextField)c;t.setText("");}
						else if(c instanceof TextArea){t1=(TextArea)c;t1.setText("");}
							}}
				card.show(adm_frame,"adm_view_sales_panel");}});

 /////////////////employee frame start/////////////

		MenuBar emp_menuBar =new MenuBar();
		Menu emp_new=new Menu("New");
		Menu emp_view=new Menu("View");
		Menu emp_options=new Menu("More");

		MenuItem emp_view_sales=new MenuItem("Sales");
		MenuItem emp_view_prod=new MenuItem("Products");
		//MenuItem emp_view_deptt=new MenuItem("Department");
		MenuItem emp_view_order=new MenuItem("Orders");

		MenuItem emp_account=new MenuItem("Account");      
		MenuItem emp_logout=new MenuItem("Logout");        
		MenuItem emp_sell=new MenuItem("Sell");
		MenuItem emp_order=new MenuItem("Order");

		emp_menuBar.setFont(new Font("Serif",Font.BOLD,16));

		emp_view.add(emp_view_sales);
		//emp_view.add(emp_view_deptt);
		emp_view.add(emp_view_prod);
		emp_view.add(emp_view_order);

		emp_new.add(emp_sell);
		emp_new.add(emp_order);
		emp_menuBar.add(emp_new);
		emp_menuBar.add(emp_view);
		emp_options.add(emp_account);
		emp_options.add(emp_logout);
		emp_menuBar.add(emp_options);

		emp_frame.setMenuBar(emp_menuBar);
		emp_frame.addWindowListener(new WindowCloser());
		emp_logout.addActionListener(new Logout());

 //////panels of employee start///
		Panel emp_home_panel=new Panel(new GridBagLayout());
		Panel emp_sell_panel=new Panel(new GridBagLayout());
		Panel emp_order_panel=new Panel(new GridBagLayout());
		Panel emp_view_sales_panel=new Panel(new GridBagLayout());
		Panel emp_view_order_panel=new Panel(new GridBagLayout());
		Panel emp_view_products_panel=new Panel(new GridBagLayout());
		Panel emp_account_panel=new Panel(new GridBagLayout());

		//emp_home_panel.setBackground(Color.RED);
		emp_home_panel.add(new Label("Welcome to Employee window"));

		emp_frame.add(emp_home_panel,"emp_home_panel");
		emp_frame.add(emp_sell_panel,"emp_sell_panel");
		emp_frame.add(emp_order_panel,"emp_order_panel");
		emp_frame.add(emp_view_sales_panel,"emp_view_sales_panel");
		emp_frame.add(emp_view_order_panel,"emp_view_order_panel");
		emp_frame.add(emp_view_products_panel,"emp_view_product_panel");
		emp_frame.add(emp_account_panel,"emp_account_panel");

 ////////////// actoinslisteners of employee menubar start/////
		emp_account.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e ){
				emp_account_panel.removeAll();
				setupEmp_account_panel(emp_account_panel);
			card.show(emp_frame,"emp_account_panel");
				}
			});
		emp_sell.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ee ){
				if(emp_sell_panel.getComponentCount()==0)
					setupEmp_sell_panel(emp_sell_panel);
				else{TextField t;
					for(Component c:emp_sell_panel.getComponents()){
						if(c instanceof TextField){t=(TextField)c;t.setText("");}
						}}
				card.show(emp_frame,"emp_sell_panel");
					}		
					});
		emp_order.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ee ){
				if(emp_order_panel.getComponentCount()==0)
					setupEmp_order_panel(emp_order_panel);
				else{TextField t;
				for(Component c:emp_order_panel.getComponents()){
				if(c instanceof TextField){t=(TextField)c;t.setText("");}
					}}
				card.show(emp_frame,"emp_order_panel");
					}
				});


		emp_view_order.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ee ){
				if(emp_view_order_panel.getComponentCount()==0)
					setupEmp_view_order_panel(emp_view_order_panel);
				else{TextField t;
					TextArea t1;
					for(Component c:emp_view_order_panel.getComponents()){
						if(c instanceof TextField ){t=(TextField)c;t.setText("");}
						else if(c instanceof TextArea){t1=(TextArea)c;t1.setText("");}
							}}
				card.show(emp_frame,"emp_view_order_panel");
					}
				});
		emp_view_sales.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ee ){
				if(emp_view_sales_panel.getComponentCount()==0)
					setupEmp_view_sales_panel(emp_view_sales_panel);
				else{TextField t;
					TextArea t1;
					for(Component c:emp_view_sales_panel.getComponents()){
						if(c instanceof TextField ){t=(TextField)c;t.setText("");}
						else if(c instanceof TextArea){t1=(TextArea)c;t1.setText("");}
							}}
				card.show(emp_frame,"emp_view_sales_panel");
					}
				});
		emp_view_prod.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ee ){
				if(emp_view_products_panel.getComponentCount()==0)
					setupEmp_view_products_panel(emp_view_products_panel);
					//System.out.println("okaY");
				else{TextField t;
					TextArea t1;
					for(Component c:emp_view_products_panel.getComponents()){
						if(c instanceof TextField ){t=(TextField)c;t.setText("");}
						else if(c instanceof TextArea){t1=(TextArea)c;t1.setText("");}
							}}
				card.show(emp_frame,"emp_view_product_panel");
					}
				});

 //////////////////////////////////////////////////////////////


	}

 /////////////////////////////employee panels setup/////////////////
	public static void setupEmp_account_panel(Panel x){
		x.setLayout(new GridBagLayout());
		GridBagConstraints gbc=new GridBagConstraints();
		gbc.gridx=0;gbc.gridy=0;gbc.gridwidth=1;gbc.gridheight=1;gbc.anchor=GridBagConstraints.CENTER;
		gbc.weightx=4;gbc.weighty=1;
		x.setFont(new Font("Serif",Font.BOLD,15));
		Label name=new Label("Name");
		Label doj=new Label("Date of Joining");
		Label empId=new Label("User ID");
		Label salary=new Label("Salary");
		Label gender=new Label("Gender");
		Label deptt=new Label("Department");
		Label dob=new Label("Date Of Birth");
		Label contact=new Label("Contact No");
		Label password=new Label("User Password");

		TextField _name=new TextField(30);
		_name.setEditable(false);
		TextField _doj=new TextField(30);
		_doj.setEditable(false);
		TextField _empId=new TextField(30);
		_empId.setEditable(false);
		TextField _salary=new TextField(30);
		_salary.setEditable(false);
		TextField _gender=new TextField(30);
		_gender.setEditable(false);
		TextField _deptt=new TextField(30);
		_deptt.setEditable(false);
		TextField _dob=new TextField(30);
		_dob.setEditable(false);
		TextField _contact=new TextField(30);
		
		TextField _password=new TextField(30);
		
		

		gbc.gridy=0;x.add(name,gbc);
		gbc.gridy=1;x.add(doj,gbc);
		gbc.gridy=2;x.add(empId,gbc);
		gbc.gridy=3;x.add(salary,gbc);
		gbc.gridy=4;x.add(gender,gbc);
		gbc.gridy=5;x.add(deptt,gbc);
		gbc.gridy=6;x.add(dob,gbc);
		gbc.gridy=7;x.add(contact,gbc);
		gbc.gridy=8;x.add(password,gbc);
		gbc.gridx=1;
		gbc.gridy=0;x.add(_name,gbc);
		gbc.gridy=1;x.add(_doj,gbc);
		gbc.gridy=2;x.add(_empId,gbc);
		gbc.gridy=3;x.add(_salary,gbc);
		gbc.gridy=4;x.add(_gender,gbc);
		gbc.gridy=5;x.add(_deptt,gbc);
		gbc.gridy=6;x.add(_dob,gbc);
		gbc.gridy=7;x.add(_contact,gbc);
		gbc.gridy=8;x.add(_password,gbc);

		Button save=new Button("Save");
		Button exit=new Button("Exit");
		//save.setSize()
		gbc.gridy=9;
		gbc.gridx=0;
		gbc.gridwidth=1;
		x.add(save,gbc);
		gbc.gridx=1;
		x.add(exit,gbc);
		save.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent ac){
					try{ps = con.prepareStatement("update EMPLOYEE set Contact=? where EmpID='"+Mall.userId+"'");
						ps.setString(1,_contact.getText());
						ps = con.prepareStatement("update USERS set Password=? where UserID='"+Mall.userId+"'");
						ps.setString(1,_password.getText());
						int j=ps.executeUpdate();
						}catch(Exception ex){ex.printStackTrace();}
					}});

		
		try{
		rs=stmt.executeQuery("select * from EMPLOYEE where EmpID='"+Mall.userId+"'");
		rs1=stmt1.executeQuery("select Password from USERS where UserID='"+Mall.userId+"'");
		//System.out.println("gonna print rs       "+Mall.userId);
		//System.out.println(" "+user_tf.getText());
		while(rs.next() && rs1.next()){
		
					_name.setText(rs.getString(1));
					try{_doj.setText(rs.getString(2)); }catch(Exception exx){}    
					_empId.setText(rs.getString(3));
					_salary.setText(rs.getString(4));
					_gender.setText(rs.getString(5));
					_deptt.setText(rs.getString(6));
					try{_dob.setText(rs.getString(7));}catch(Exception ex){}
					_contact.setText(rs.getString(8));
					_password.setText(rs1.getString(1));

						}		
						}
		catch (Exception ex){ex.printStackTrace();}

		exit.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			Frame f=(Frame)(x.getParent());
			card.show(f,"emp_home_panel");
			}});
		
	}

	public static void setupEmp_sell_panel(Panel x){
	
		x.setFont(new Font("Serif",Font.BOLD,16));
		Label new_sell_prodID=new Label("Product ID");
		Label new_sell_units =new Label("Units");
		Label new_sell_amount =new Label("Amount");
		Label new_sell_name =new Label("Product Name");
		Label new_sell_brand=new Label("Brand");
		Label new_sell_price=new Label("Price");
		
		TextField _new_sell_prodID=new TextField(20);
		TextField _new_sell_units =new TextField(20);
		TextField _new_sell_amount =new TextField(20);
		TextField _new_sell_name =new TextField(20);
		TextField _new_sell_brand=new TextField(20);
		TextField _new_sell_price=new TextField(20);
		
		_new_sell_brand.setEditable(false);
		_new_sell_name.setEditable(false);
		_new_sell_price.setEditable(false);
		_new_sell_amount.setEditable(false);

		Button new_sell_goID=new Button(" GO ");
		Button new_sell_goQ=new Button(" GO ");
		Button new_sell_ok =new Button(" OK ");
		Button  new_sell_exit=new Button("EXIT");

		x.setLayout(new GridBagLayout());
		GridBagConstraints gbc=new GridBagConstraints();
		gbc.gridx=0;gbc.gridy=0;gbc.gridwidth=1;gbc.gridheight=1;gbc.anchor=GridBagConstraints.CENTER;
		gbc.weightx=4;gbc.weighty=1;

		x.add(new_sell_prodID,gbc);
		gbc.gridy=1;x.add(new_sell_name,gbc);
		gbc.gridy=2;x.add(new_sell_brand,gbc);
		gbc.gridy=3;x.add(new_sell_price,gbc);
		gbc.gridy=4;x.add(new_sell_units,gbc);
		gbc.gridy=5;x.add(new_sell_amount,gbc);
		gbc.gridx=1;
		gbc.gridy=0;x.add(_new_sell_prodID,gbc);
		gbc.gridy=1;x.add(_new_sell_name,gbc);
		gbc.gridy=2;x.add(_new_sell_brand,gbc);
		gbc.gridy=3;x.add(_new_sell_price,gbc);
		gbc.gridy=4;x.add(_new_sell_units,gbc);
		gbc.gridy=5;x.add(_new_sell_amount,gbc);

		gbc.gridx=2;	gbc.gridy=0;	x.add(new_sell_goID,gbc);
		gbc.gridy=4;	gbc.gridx=2;	x.add(new_sell_goQ,gbc);
		gbc.gridy=6;	gbc.gridx=0;	x.add(new_sell_exit,gbc);
		gbc.gridx=1;	x.add(new_sell_ok,gbc);

		Button diaOk=new Button("OK");
		new_sell_exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Frame f=(Frame)(x.getParent());
				card.show(f,"emp_home_panel");
				}});
		
		new_sell_goID.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ee){
				try{

					String pid=_new_sell_prodID.getText();
					rs=stmt.executeQuery("select Name ,Brand,Price ,DepttID from PRODUCT where ProdID='"+pid+"'");
					rs1=stmt1.executeQuery("select DepttID from EMPLOYEE where EmpID='"+Mall.userId+"'");

					while(rs.next()&& rs1.next()){
						if(rs1.getString(1).equals(rs.getString(4))){
						_new_sell_name.setText(rs.getString(1));
						_new_sell_brand.setText(rs.getString(2));
						_new_sell_price.setText(rs.getString(3));}
						else{
							System.out.println("Invalid product ,not in your departement");
						}

						
					}
				}catch(Exception ex){System.out.println(ex);}
				}}); 
		new_sell_goQ.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ace){
				try{dia=new Dialog(emp_frame,"Attention",true);
					dia.setSize(300,200);
					dia.setLayout(new GridLayout(2,1));
					int i=0;
					int pr=0,qn=0;
					String pid=_new_sell_prodID.getText();
					rs=stmt.executeQuery("select Price,QuantityInStock from PRODUCT where ProdID='"+pid+"'");
					try{i=Integer.parseInt(_new_sell_units.getText());
						if(0>=i)throw new IOException("");		
						
					}catch(Exception ex){
							dia.add(new Label("Invalid Quantity"));
							throw ex;
					}
					while(rs.next()){pr=Integer.parseInt(rs.getString(1));
							qn=Integer.parseInt(rs.getString(2));}
				
							_new_sell_amount.setText(i*pr+"");
				
					}catch(Exception ce){dia.add(diaOk);
						dia.setVisible(true);}
			}
		});
		new_sell_ok.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				try{
					dia=new Dialog(emp_frame,"Attention",true);
					dia.setSize(300,200);
					dia.setLayout(new GridLayout(2,1));
					String department="";
					int qn=0;
					while(rs.next())
							qn=Integer.parseInt(rs.getString(1));
					rs=stmt.executeQuery("select QuantityInStock from PRODUCT where prodID ='"+_new_sell_prodID.getText()+"'");
					try{
						if(0>=Integer.parseInt(_new_sell_units.getText()))throw new IOException("");		
						
					}catch(Exception ex){
							dia.add(new Label("Invalid Quantity"));
							throw ex;
					}
					if(qn>=Integer.parseInt(_new_sell_units.getText())){
					ps = con.prepareStatement("insert into SALES_RECORD (ProdID,Date_Time,Units,Price,AmountTotal) values(?,?,?,?,?) ");
						ps.setString(1,_new_sell_prodID.getText());
						ps.setString(2,date+"");
						ps.setString(3,_new_sell_units.getText());
						ps.setString(4,_new_sell_price.getText());
						ps.setString(5,_new_sell_amount.getText());
						//ps.setString(6,department);
						//System.out.println(department+" sdfghj");

						int j1=ps.executeUpdate();
						ps = con.prepareStatement("update PRODUCT set QuantityInStock='"+(qn-Integer.parseInt(_new_sell_units.getText())) +"' where ProdID='"+_new_sell_prodID.getText()+"'");
						j1=ps.executeUpdate();
						//System.out.println(qn);
		
						int j=ps.executeUpdate();
						}
						else{
							
							dia.add(new Label("Shortage of product"));
							
						}
					}catch(Exception ex){
						dia.add(diaOk);
						dia.setVisible(true);}

			}
		});
		diaOk.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){dia.setVisible(false);}});

	}
	public static void setupEmp_order_panel(Panel x){
		x.setFont(new Font("Serif",Font.BOLD,16));

		Label new_order_prodID=new Label("Product ID");
		Label new_order_units =new Label("Units");
		Label new_order_amount =new Label("Amount");
		Label new_order_name =new Label("Product Name");
		Label new_order_brand=new Label("Brand");
		Label new_order_cost=new Label("Cost");
		Label new_order_sname=new Label("Supplier");

		TextField _new_order_prodID=new TextField(20);
		TextField _new_order_units =new TextField(20);
		TextField _new_order_amount =new TextField(20);
		TextField _new_order_name =new TextField(20);
		TextField _new_order_brand=new TextField(20);
		TextField _new_order_cost=new TextField(20);
		TextField _new_order_sname=new TextField(20);

		_new_order_brand.setEditable(false);
		_new_order_name.setEditable(false);
		_new_order_cost.setEditable(false);
		_new_order_amount.setEditable(false);
		_new_order_sname.setEditable(false);

		Button new_order_goID=new Button(" GO ");
		Button new_order_goQ=new Button(" GO ");
		Button new_order_ok =new Button(" OK ");
		Button  new_order_exit=new Button("EXIT");

		x.setLayout(new GridBagLayout());
		//this.setVisible(true);
		//setSize(500,400);
		GridBagConstraints gbc=new GridBagConstraints();
		gbc.gridx=0;gbc.gridy=0;gbc.gridwidth=1;gbc.gridheight=1;gbc.anchor=GridBagConstraints.CENTER;
		gbc.weightx=4;gbc.weighty=1;

		x.add(new_order_prodID,gbc);
		gbc.gridy=1;x.add(new_order_name,gbc);
		gbc.gridy=2;x.add(new_order_brand,gbc);
		gbc.gridy=3;x.add(new_order_cost,gbc);
		gbc.gridy=4;x.add(new_order_sname,gbc);
		gbc.gridy=5;x.add(new_order_units,gbc);
		gbc.gridy=6;x.add(new_order_amount,gbc);
		gbc.gridx=1;
		gbc.gridy=0;x.add(_new_order_prodID,gbc);
		gbc.gridy=1;x.add(_new_order_name,gbc);
		gbc.gridy=2;x.add(_new_order_brand,gbc);
		gbc.gridy=3;x.add(_new_order_cost,gbc);
		gbc.gridy=4;x.add(_new_order_sname,gbc);
		gbc.gridy=5;x.add(_new_order_units,gbc);
		gbc.gridy=6;x.add(_new_order_amount,gbc);
		gbc.gridx=2;
		gbc.gridy=0;x.add(new_order_goID,gbc);
		gbc.gridy=5;
		gbc.gridx=2;x.add(new_order_goQ,gbc);
		gbc.gridy=7;
		gbc.gridx=0;x.add(new_order_exit,gbc);
		gbc.gridx=1;x.add(new_order_ok,gbc);

		Button diaOk=new Button("OK");
		new_order_exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Frame f=(Frame)(x.getParent());
				card.show(f,"emp_home_panel");
			}});

		new_order_ok.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				try{
					//String department="";
					int qn=0;
					rs=stmt.executeQuery("select QuantityInStock from PRODUCT where prodID ='"+_new_order_prodID.getText()+"'");
					while(rs.next()){qn=Integer.parseInt(rs.getString(1));}
					rs1=stmt1.executeQuery("select p.ProdID from PRODUCT p,DEPARTMENT d where p.DepttID=d.DepttID && d.ManagerID='"+Mall.userId+"'");
					if(!rs1.next()){throw new IOException("not in your deptt");}
					ps = con.prepareStatement("insert into _ORDER (ProdID,OrderDate,Quantity,Cost,AmountTotal) values(?,?,?,?,?) ");
					ps.setString(1,_new_order_prodID.getText());
					ps.setString(2,date+"");
					ps.setString(3,_new_order_units.getText());
					ps.setString(4,_new_order_cost.getText());
					ps.setString(5,_new_order_amount.getText());
						
					//	System.out.println(department+" sdfghj");

						int j1=ps.executeUpdate();
						ps = con.prepareStatement("update PRODUCT set QuantityInStock='"+(qn+Integer.parseInt(_new_order_units.getText())) +"' where ProdID='"+_new_order_prodID.getText()+"'");
						j1=ps.executeUpdate();
						System.out.println(qn);
		
						int j=ps.executeUpdate();
						
						
					}catch(Exception ex){System.out.println(ex);}

			}
		});

		new_order_goQ.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ace){
				try{dia=new Dialog(emp_frame,"Attention",true);
					dia.setSize(300,200);
					dia.setLayout(new GridLayout(2,1));
					int i=0;
					int pr=0,qn=0;
					String pid=_new_order_prodID.getText();
					rs=stmt.executeQuery("select Cost from PRODUCT where ProdID='"+pid+"'");
					try{i=Integer.parseInt(_new_order_units.getText());
						if(0>=i)throw new IOException("");		
						
					}catch(Exception ex){
							dia.add(new Label("Invalid Quantity"));
							throw ex;
					}
					while(rs.next()) pr=Integer.parseInt(rs.getString(1));
					_new_order_amount.setText(i*pr+"");
				
					}catch(Exception ce){
						dia.add(diaOk);
						dia.setVisible(true);}
			}
		});
		diaOk.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){dia.setVisible(false);}});


		new_order_goID.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ee){
				try{

					String pid=_new_order_prodID.getText();
					rs=stmt.executeQuery("select Name ,Brand,Cost ,DepttID,SuppID from PRODUCT where ProdID='"+pid+"'");
					rs1=stmt1.executeQuery("select DepttID from EMPLOYEE where EmpID='"+Mall.userId+"'");

					while(rs.next()&& rs1.next()){
						if(rs1.getString(1).equals(rs.getString(4))){
							Statement stmt2=con.createStatement();
						ResultSet rs2=stmt2.executeQuery("select Name from SUPPLIER where SuppID='"+rs.getString(5)+"'");
						while(rs2.next()){_new_order_sname.setText(rs2.getString(1));}
						_new_order_name.setText(rs.getString(1));
						_new_order_brand.setText(rs.getString(2));
						_new_order_cost.setText(rs.getString(3));}
						else{System.out.println("Invalid product ,not in your departement");}

						
					}
				}catch(Exception ex){System.out.println(ex);}
				}});
		}



	public static void setupEmp_view_order_panel(Panel x){
		x.setFont(new Font("Serif",Font.BOLD,16));
		x.setLayout(new GridBagLayout());
		
		Label emp_view_order_selectBy=new Label("  Sort by-");
		Label emp_view_order_orderBy=new Label("   Order by-");
	
	
		Choice c1=new Choice();
		Choice c2=new Choice();
		Choice c3=new Choice();
		c1.addItem("None");
		c1.addItem("Order no");c1.addItem("Product ID");c1.addItem("Product Name");c1.addItem("Quantities");c1.addItem("Product Cost");
		c1.addItem("Supplier ID");c1.addItem("Product Brand");c1.addItem("Order Date");c1.addItem("Total Cost");

		c2.addItem("Order no");c2.addItem("Product ID");c2.addItem("Product Name");c2.addItem("Quantities");c2.addItem("Product Cost");
		c2.addItem("Supplier ID");c2.addItem("Product Brand");c2.addItem("Order Date");c2.addItem("Total Cost");

		c3.add("Ascending");
		c3.add("Descending");

		Button emp_view_order_ok=new Button ("  OK  ");
		TextField _emp_view_order_selectBy=new TextField(20);

		GridBagConstraints gbc=new GridBagConstraints();
		gbc.gridx=0;gbc.gridy=0;gbc.gridwidth=1;gbc.gridheight=1;gbc.anchor=GridBagConstraints.CENTER;
		gbc.weightx=10;gbc.weighty=1;


		gbc.gridx=0; x.add(emp_view_order_selectBy,gbc);
		gbc.gridx=1;x.add(c1,gbc);
		gbc.gridx=2; x.add(_emp_view_order_selectBy,gbc);
		gbc.gridx=3; x.add(emp_view_order_orderBy,gbc);
		gbc.gridx=4; x.add(c2,gbc);
		gbc.gridx=5; x.add(c3,gbc);
		gbc.gridx=8; x.add(emp_view_order_ok,gbc);
		
		gbc.gridx=0;
		gbc.gridy=1;gbc.gridwidth=10;
		//gbc.weightx=100;
		gbc.fill = GridBagConstraints.BOTH;
		JTable tbl=new JTable(1,1);
		JScrollPane sp=new JScrollPane(tbl);
		sp.setBackground(Color.RED);
		tbl.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		x.add(sp,gbc);


		emp_view_order_ok.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int sort=c1.getSelectedIndex();
				String value=_emp_view_order_selectBy.getText();
				int orderBy=c2.getSelectedIndex();
				int order=c3.getSelectedIndex();
				String query="select  o.OrderID,o.OrderDate,o.prodID,p.Name,p.Brand,p.SuppID,o.Cost,o.Quantity,o.AmountTotal from _ORDER o, PRODUCT p,DEPARTMENT  d where(d.ManagerID='"+Mall.userId+"' &&p.DepttID=d.DepttID && o.ProdID=p.ProdID  ";
				String orderBy_string=" order by o.OrderID";
				String orderType=" ASC";
				String cond=" ) ";
				switch(orderBy){
					case 0:orderBy_string=" order by o.OrderID";break;
					case 1:orderBy_string=" order by o.ProdID";break;
					case 2:orderBy_string=" order by p.Name";break;
					case 3:orderBy_string=" order by o.Quantity";break;
					case 4:orderBy_string=" order by o.Cost";break;
					case 5:orderBy_string=" order by p.SuppID";break;
					case 6:orderBy_string=" order by p.Brand";break;
					case 7:orderBy_string=" order by o.OrderDateSaleNo";break;
					case 8:orderBy_string=" order by o.AmountTotal";break;
					default:orderBy_string=" order by o.OrderID";
						
				}
				if(order==1){
					orderType=" DESC";
				}
				switch(sort){
					case 1:cond="&& o.OrderID='"+_emp_view_order_selectBy.getText()+"')"		;break;
					case 2:cond=" && o.ProdID='"+_emp_view_order_selectBy.getText()+"')"		;break;
					case 3:cond=" && p.Name='"+_emp_view_order_selectBy.getText()+"')"		;break;
					case 4:cond=" && o.Quantity='"+_emp_view_order_selectBy.getText()+"')"	;break;
					case 5:cond=" && o.Cost='"+_emp_view_order_selectBy.getText()+"')"		;break;
					case 6:cond=" && p.SuppID='"+_emp_view_order_selectBy.getText()+"')"		;break;
					case 7:cond=" && p.Brand='"+_emp_view_order_selectBy.getText()+"')"		;break;
					case 8:cond=" && o.OrderDate='"+_emp_view_order_selectBy.getText()+"')"		;break;
					case 9:cond=" && o.AmountTotal='"+_emp_view_order_selectBy.getText()	+"')"	;break;
					default:cond=" )"		;
				}
				try{
				vector2=new Vector<Vector<String>>();
				int count=0;
				rs=stmt.executeQuery(query+cond+orderBy_string+orderType);
				while(rs.next()){
				//	String newTemp="";
					count++;
					vector=new Vector<String>();
					for(int i=1;i<10;i++)vector.add(rs.getString(i));
					vector2.add(vector);
					}
				vector=new Vector<String>();
				vector.add("Order No.");vector.add("Date");vector.add("ProductID");vector.add("Prod.Name");vector.add("Brand");
				vector.add("SuppID");vector.add("Cost");vector.add("Quantity");vector.add("TotalCost");
				//System.out.println(tbl.getModel().getClass());
				DefaultTableModel dtm=new DefaultTableModel(vector2,vector);
				tbl.setModel(dtm);
				dtm.fireTableDataChanged();
				
				}catch(Exception eee){eee.printStackTrace();}

				}
			});
	
			}
	

	public static void setupEmp_view_sales_panel(Panel x){
		x.setFont(new Font("Serif",Font.BOLD,16));
		x.setLayout(new GridBagLayout());
		
		Label emp_view_sales_selectBy=new Label("  Sort by-");
		Label emp_view_sales_orderBy=new Label("   Order by-");
	
		Choice c1=new Choice();
		Choice c2=new Choice();
		Choice c3=new Choice();
		c1.addItem("None");
		c1.addItem("Sell no");c1.addItem("Product ID");c1.addItem("Product Name");c1.addItem("Quantity");c1.addItem("Price");
		c1.addItem("Product Brand");c1.addItem("Sell Date");c1.addItem("Total Amount");

		c2.addItem("Sell no");c2.addItem("Product ID");c2.addItem("Product Name");c2.addItem("Quantity");c2.addItem("Price");
		c2.addItem("Product Brand");c2.addItem("Sell Date");c2.addItem("Total Amount");

		c3.add("Ascending");
		c3.add("Descending");

		Button emp_view_sales_ok=new Button ("  OK  ");
		TextField _emp_view_sales_selectBy=new TextField(20);

		GridBagConstraints gbc=new GridBagConstraints();
		gbc.gridx=0;gbc.gridy=0;gbc.gridwidth=1;gbc.gridheight=1;gbc.anchor=GridBagConstraints.CENTER;
		gbc.weightx=10;gbc.weighty=1;


		gbc.gridx=0; x.add(emp_view_sales_selectBy,gbc);
		gbc.gridx=1;x.add(c1,gbc);
		gbc.gridx=2; x.add(_emp_view_sales_selectBy,gbc);
		gbc.gridx=3; x.add(emp_view_sales_orderBy,gbc);
		gbc.gridx=4; x.add(c2,gbc);
		gbc.gridx=5; x.add(c3,gbc);
		gbc.gridx=8; x.add(emp_view_sales_ok,gbc);

		gbc.gridx=0;
		gbc.gridy=1;gbc.gridwidth=10;
		//gbc.weightx=100;
		gbc.fill = GridBagConstraints.BOTH;
		JTable tbl=new JTable(1,1);
		JScrollPane sp=new JScrollPane(tbl);
		sp.setBackground(Color.RED);
		tbl.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		x.add(sp,gbc);

		
		emp_view_sales_ok.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int sort=c1.getSelectedIndex();
				String value=_emp_view_sales_selectBy.getText();
				int orderBy=c2.getSelectedIndex();
				int order=c3.getSelectedIndex();
				String query="select o.SaleNo,o.Date_Time,o.prodID,p.Name,p.Brand,o.Price,o.Units,o.AmountTotal from SALES_RECORD o, PRODUCT p,DEPARTMENT d where( o.ProdID=p.ProdID && p.DepttID=d.DepttID && d.ManagerID='"+ Mall.userId+"' ";
				String orderBy_string=" order by o.SaleNo";
				String orderType=" ASC";
				String cond=" ) ";
				switch(orderBy){
					case 0:orderBy_string=" order by o.SaleNo";break;
					case 1:orderBy_string=" order by o.ProdID";break;
					case 2:orderBy_string=" order by p.Name";break;
					case 3:orderBy_string=" order by o.Units";break;
					case 4:orderBy_string=" order by o.Price";break;
					
					case 5:orderBy_string=" order by p.Brand";break;
					case 6:orderBy_string=" order by o.Date_Time";break;
					case 7:orderBy_string=" order by o.AmountTotal";break;
					default:orderBy_string=" order by o.SaleNo";
						
				}
				if(order==1){
					orderType=" DESC";
				}
				switch(sort){
					case 1:cond="&& o.SaleNo='"+_emp_view_sales_selectBy.getText()+"')"		;break;
					case 2:cond=" && o.ProdID='"+_emp_view_sales_selectBy.getText()+"')"		;break;
					case 3:cond=" && p.Name='"+_emp_view_sales_selectBy.getText()+"')"		;break;
					case 4:cond=" && o.Units='"+_emp_view_sales_selectBy.getText()+"')"	;break;
					case 5:cond=" && o.Price='"+_emp_view_sales_selectBy.getText()+"')"		;break;
					
					case 6:cond=" && p.Brand='"+_emp_view_sales_selectBy.getText()+"')"		;break;
					case 7:cond=" && o.Date_Time='"+_emp_view_sales_selectBy.getText()+"')"		;break;
					case 8:cond=" && o.AmountTotal='"+_emp_view_sales_selectBy.getText()	+"')"	;break;
					default:cond=" )"		;
				}
				try{
				vector2=new Vector<Vector<String>>();
				int count=0;
				rs=stmt.executeQuery(query+cond+orderBy_string+orderType);
				while(rs.next()){
				//	String newTemp="";
					count++;
					vector=new Vector<String>();
					for(int i=1;i<9;i++)vector.add(rs.getString(i));
					vector2.add(vector);
					}
				vector=new Vector<String>();
				vector.add("Sale No.");vector.add("Date");vector.add("ProductID");vector.add("Prod.Name");vector.add("Brand");
				vector.add("Price");vector.add("Quantity");vector.add("TotalCost");
				//System.out.println(tbl.getModel().getClass());
				DefaultTableModel dtm=new DefaultTableModel(vector2,vector);
				tbl.setModel(dtm);
				dtm.fireTableDataChanged();
				}catch(Exception eee){System.out.println(eee);}

				}
			});
	
		}
	

	public static void setupEmp_view_products_panel(Panel x){
		x.setFont(new Font("Serif",Font.BOLD,16));
		x.setLayout(new GridBagLayout());
		
		Label emp_view_product_selectBy=new Label("  Sort by-");
		Label emp_view_product_orderBy=new Label("   Order by-");
		
		Choice c1=new Choice();
		Choice c2=new Choice();
		Choice c3=new Choice();
		c1.addItem("None");
		c1.addItem("Product ID");c1.addItem("Product Name");c1.addItem("Quantities");c1.addItem("Product Cost");
		c1.addItem("Supplier ID");c1.addItem("Product Brand");c1.addItem("Product Price");c1.addItem("Supplier Name");
		c2.add("None");
		c2.addItem("Product ID");c2.addItem("Product Name");c2.addItem("Quantities");c2.addItem("Product Cost");
		c2.addItem("Supplier ID");c2.addItem("Product Brand");c2.addItem("Product Price");c2.addItem("Supplier Name");

		c3.add("Ascending");
		c3.add("Descending");

		Button emp_view_product_ok=new Button ("  OK  ");
		TextField _emp_view_product_selectBy=new TextField(20);

		GridBagConstraints gbc=new GridBagConstraints();
		gbc.gridx=0;gbc.gridy=0;gbc.gridwidth=1;gbc.gridheight=1;gbc.anchor=GridBagConstraints.CENTER;
		gbc.weightx=10;gbc.weighty=1;


		gbc.gridx=0; x.add(emp_view_product_selectBy,gbc);
		gbc.gridx=1;x.add(c1,gbc);
		gbc.gridx=2; x.add(_emp_view_product_selectBy,gbc);
		gbc.gridx=3; x.add(emp_view_product_orderBy,gbc);
		gbc.gridx=4; x.add(c2,gbc);
		gbc.gridx=5; x.add(c3,gbc);
		gbc.gridx=8; x.add(emp_view_product_ok,gbc);

		gbc.gridx=0;
		gbc.gridy=1;gbc.gridwidth=10;
		//gbc.weightx=100;
		gbc.fill = GridBagConstraints.BOTH;
		JTable tbl=new JTable(1,1);
		JScrollPane sp=new JScrollPane(tbl);
		sp.setBackground(Color.RED);
		tbl.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		x.add(sp,gbc);
		
		emp_view_product_ok.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int sort=c1.getSelectedIndex();
				String value=_emp_view_product_selectBy.getText();
				int orderBy=c2.getSelectedIndex();
				int order=c3.getSelectedIndex();
				String query="select p.prodID,p.Name,p.QuantityInStock,p.Cost,p.SuppID,p.Price ,s.Name,p.Brand from  SUPPLIER s, PRODUCT p,DEPARTMENT  d where( s.suppID=p.SuppID && p.ProdID<>'Unknown' && p.DepttId=d.DepttID && d.ManagerID='"+Mall.userId+ "' ";
				String orderBy_string=" ";
				String orderType=" ASC";
				String cond=" ) ";
				switch(orderBy){
					case 1:orderBy_string=" order by p.ProdID";break;
					case 2:orderBy_string=" order by p.Name";break;
					case 3:orderBy_string=" order by p.QuantityInStock";break;
					case 4:orderBy_string=" order by p.Cost";break;
					case 5:orderBy_string=" order by p.SuppID";break;
					case 6:orderBy_string=" order by p.Brand";break;
					case 7:orderBy_string=" order by P.Price";break;
					case 8:orderBy_string=" order by s.Name";break;
					default:orderBy_string=" ";
						
				}
				if(order==1){
					orderType=" DESC";
				}
				switch(sort){
					case 1:cond=" && p.ProdID='"+_emp_view_product_selectBy.getText()+"')"		;break;
					case 2:cond=" && p.Name='"+_emp_view_product_selectBy.getText()+"')"		;break;
					case 3:cond=" && p.QuantityInStock='"+_emp_view_product_selectBy.getText()+"')"	;break;
					case 4:cond=" && p.Cost='"+_emp_view_product_selectBy.getText()+"')"		;break;
					case 5:cond=" && p.SuppID='"+_emp_view_product_selectBy.getText()+"')"		;break;
					case 6:cond=" && p.Brand='"+_emp_view_product_selectBy.getText()+"')"		;break;
					case 7:cond=" && p.Price='"+_emp_view_product_selectBy.getText()+"')"		;break;
					case 8:cond=" && s.Name='"+_emp_view_product_selectBy.getText()	+"')"	;break;
					default:cond=" )"		;
				}
				if(orderBy_string.equals(" "))orderType="";
				try{
				vector2=new Vector<Vector<String>>();
				rs=stmt.executeQuery(query+cond+orderBy_string+orderType);
				while(rs.next()){
					vector=new Vector<String>();
					for(int i=1;i<9;i++)vector.add(rs.getString(i));
					vector2.add(vector);
					}
				vector=new Vector<String>();
				vector.add("ProductID");vector.add("Prod.Name");vector.add("QuantityInStock");vector.add("Cost");
				vector.add("SupplierID");vector.add("Price");vector.add("Supp. Name");vector.add("Brand");
				//System.out.println(tbl.getModel().getClass());
				DefaultTableModel dtm=new DefaultTableModel(vector2,vector);
				tbl.setModel(dtm);
				dtm.fireTableDataChanged();
				}catch(Exception eee){System.out.println(eee);}

				}
			});
	
		}

 //////////////admin panels setup//////////////////////////
	public static void setupAdm_account_panel(Panel x){
		x.setLayout(new GridBagLayout());
		Label userID=new Label("UserID");
		Label password=new Label("Password");
		TextField _userID=new TextField(20);
		TextField _password=new TextField(20);
		Button exit=new Button("Exit");
		Button ok=new Button("OK");

		_userID.setEditable(false);

		GridBagConstraints gbc=new GridBagConstraints();
		gbc.gridx=0;gbc.gridy=0;gbc.gridwidth=1;gbc.gridheight=1;gbc.anchor=GridBagConstraints.CENTER;
		gbc.weightx=4;gbc.weighty=1;

		x.add(userID,gbc);
		gbc.gridx=1;x.add(_userID,gbc);
		gbc.gridy=1;
		x.add(_password,gbc);
		gbc.gridx=0;x.add(password,gbc);
		gbc.gridy=2;
		x.add(exit,gbc);
		gbc.gridx=1;x.add(ok,gbc);
		try{
		rs=stmt.executeQuery("select UserID,Password from USERS where UserID='"+Mall.userId+"'");
		while(rs.next()){
			_password.setText(rs.getString(2));
			_userID.setText(rs.getString(1));	}
		}catch(Exception ex){System.out.println(ex);}




		ok.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
					ps = con.prepareStatement("update USERS set Password=? where UserID='"+Mall.userId+"'");
					ps.setString(1,_password.getText());
					int j=ps.executeUpdate();

				}catch(Exception ex){System.out.println(ex);}
			}
		});

		exit.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			Frame f=(Frame)(x.getParent());
			card.show(f,"adm_home_panel");
			}});

		}

	public static void setupAdm_new_supplier_panel(Panel x){
		GridBagConstraints gbc=new GridBagConstraints();
		gbc.gridx=0;gbc.gridy=0;gbc.gridwidth=1;gbc.gridheight=1;gbc.anchor=GridBagConstraints.CENTER;
		gbc.weightx=4;gbc.weighty=1;

		x.setFont(new Font("Serif",Font.BOLD,15));
		
		Label name=new Label("Name");
		Label suppId=new Label("Supplier ID");
		Label gender=new Label("Gender");
		Label contact=new Label("Contact");
		TextField _name=new TextField(30);
		TextField _suppId=new TextField(30);
		TextField _gender=new TextField(30);
		TextField _contact=new TextField(30);

		gbc.gridy=0;x.add(name,gbc);
		gbc.gridy=1;x.add(suppId,gbc);
		gbc.gridy=2;x.add(gender,gbc);
		gbc.gridy=3;x.add(contact,gbc);
		
		gbc.gridx=1;
		gbc.gridy=0;x.add(_name,gbc);
		gbc.gridy=1;x.add(_suppId,gbc);
		gbc.gridy=2;x.add(_gender,gbc);
		gbc.gridy=3;x.add(_contact,gbc);

		Button save=new Button("Save");
		Button exit=new Button("Exit");
		Button ok=new Button("OK");
		ok.setBounds(100,100,50,50);

		gbc.gridy=5;gbc.gridx=0;
		gbc.gridwidth=1;
		x.add(exit,gbc);
		gbc.gridx=1;
		x.add(save,gbc);
		save.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent ac){
					dia = new Dialog(adm_frame, "Attention !", true);
					dia.setLayout(new GridLayout(2,1));
					dia.setSize(300,200);
					try{

						ps = con.prepareStatement("insert into SUPPLIER values(?,?,?,?)");
						ps.setString(1,_name.getText());
						ps.setString(2,_suppId.getText());
						ps.setString(3,_gender.getText());
						ps.setString(4,_contact.getText());

						int j=ps.executeUpdate();

						System.out.println("success");
						_name.setText("");
						_suppId.setText("");
						_gender.setText("");
						_contact.setText("");
						}
						catch(Exception ex){dia.add(new Label("Failed !Duplicate ID"));
											//System.out.println(error);
											System.out.println(ex);
											dia.add(ok);
											dia.setVisible(true);
											error="";}
					}});
		ok.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){dia.setVisible(false);}});

		exit.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			Frame f=(Frame)(x.getParent());
			card.show(f,"adm_home_panel");
			}});
		}
	
	public static void setupAdm_new_prod_panel(Panel x){
		x.setLayout(new GridBagLayout());
		GridBagConstraints gbc=new GridBagConstraints();
		gbc.gridx=0;gbc.gridy=0;gbc.gridwidth=1;gbc.gridheight=1;gbc.anchor=GridBagConstraints.CENTER;
		gbc.weightx=4;gbc.weighty=1;

		x.setFont(new Font("Serif",Font.BOLD,15));
		
		Label name=new Label("Name");
		Label prodId=new Label("Product ID");
		Label price=new Label("Price");
		Label cost=new Label("Cost");
		Label deptt=new Label("Department ID");
		Label suppID=new Label("Supplier  ID");
		Label brand=new Label("Brand");
		TextField _name=new TextField(30);
		TextField _prodId=new TextField(30);
		TextField _price=new TextField(30);
		TextField _cost=new TextField(30);
		TextField _deptt=new TextField(30);
		TextField _suppID=new TextField(30);		
		TextField _brand=new TextField(30);

		
		gbc.gridy=0;x.add(prodId,gbc);
		gbc.gridy=1;x.add(name,gbc);
		gbc.gridy=2;x.add(suppID,gbc);
		gbc.gridy=3;x.add(price,gbc);
		gbc.gridy=4;x.add(cost,gbc);
		gbc.gridy=5;x.add(deptt,gbc);
		gbc.gridy=6;x.add(brand,gbc);
		gbc.gridx=1;
		gbc.gridy=0;x.add(_prodId,gbc);
		gbc.gridy=1;x.add(_name,gbc);
		gbc.gridy=2;x.add(_suppID,gbc);
		gbc.gridy=3;x.add(_price,gbc);
		gbc.gridy=4;x.add(_cost,gbc);
		gbc.gridy=5;x.add(_deptt,gbc);
		gbc.gridy=6;x.add(_brand,gbc);

		Button save=new Button("Save");
		Button exit=new Button("Exit");
		Button ok=new Button("OK");
		ok.setBounds(120,140,60,40);
		  
		gbc.gridy=8;gbc.gridx=0;
		gbc.gridwidth=1;
		x.add(exit,gbc);
		gbc.gridx=1;
		x.add(save,gbc);
		save.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent ac){
					dia = new Dialog(adm_frame, "Attention !", true);
					dia.setLayout(new GridLayout(2,1));
					dia.setSize(300,200);
					try{
						rs=stmt.executeQuery("select ProdID from PRODUCT where ProdID='"+_prodId.getText()+"'");
						if(rs.next()){
							dia.add(new Label("Falied !duplicate Product ID "));
							throw new IOException();}
						while(rs.next()){};
						rs=stmt.executeQuery("select DepttID from DEPARTMENT where DepttID='"+_deptt.getText()+"'");
						if(!rs.next()){
							dia.add(new Label("Falied ! Unknown Department ID "));
							throw new IOException();}
						while(rs.next()){};
						rs=stmt.executeQuery("select SuppID from SUPPLIER where SuppID='"+_suppID.getText()+"'");
						if(!rs.next()){
							dia.add(new Label("Falied ! Unknown Supplier ID "));
							throw new IOException();}
						while(rs.next()){};
						try{
						if(Integer.parseInt(_price.getText())<0) throw new IOException("");

						}catch(Exception aaa){dia.add(new Label("Falied ! Invalid  Price "));
								throw new IOException();}
						try{
							if(Integer.parseInt(_cost.getText())<0) throw new IOException("");

						}catch(Exception aa){
							dia.add(new Label("Falied ! Invalid Cost "));
							throw new IOException();}

						ps = con.prepareStatement("insert into PRODUCT values(?,?,?,?,?,?,?,?)");
						ps.setString(1,_name.getText());
						ps.setString(2,_prodId.getText());
						ps.setString(3,_deptt.getText());
						ps.setString(4,_price.getText());
						ps.setString(5,_brand.getText());
						ps.setString(6,"0");
						ps.setString(7,_suppID.getText());
						ps.setString(8,_cost.getText());
						
						
						int j=ps.executeUpdate();
						dia.add(new Label("Succeed !"));
						dia.add(ok);
						dia.setVisible(true);
						_name.setText("");
						_prodId.setText(""); _price.setText("");
						_cost.setText(""); _deptt.setText("");
						_suppID.setText("");	_brand.setText("");
						}
						catch(Exception ex){
											dia.add(ok);
											dia.setVisible(true);}
					}});
		ok.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){dia.setVisible(false);}});

		exit.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			Frame f=(Frame)(x.getParent());
			card.show(f,"adm_home_panel");
			}});
		}
	
	public static void setupAdm_new_deptt_panel(Panel x){
		x.setLayout(new GridBagLayout());
		GridBagConstraints gbc=new GridBagConstraints();
		gbc.gridx=0;gbc.gridy=0;gbc.gridwidth=1;gbc.gridheight=1;gbc.anchor=GridBagConstraints.CENTER;
		gbc.weightx=4;gbc.weighty=1;

		x.setFont(new Font("Serif",Font.BOLD,15));
		
		Label name=new Label("Name");
		Label mgrId=new Label("Manager ID");
		Label depttId=new Label("Department ID");
		Label pass=new Label("Manager Password");
		TextField _name=new TextField(30);
		TextField _depttId=new TextField(30);
		TextField _mgrId=new TextField(30);
		TextField _pass=new TextField(30);

		gbc.gridy=0;x.add(depttId,gbc);
		gbc.gridy=1;x.add(name,gbc);
		gbc.gridy=2;x.add(mgrId,gbc);
		gbc.gridy=3;x.add(pass,gbc);
		
		gbc.gridx=1;
		gbc.gridy=0;x.add(_depttId,gbc);
		gbc.gridy=1;x.add(_name,gbc);
		gbc.gridy=2;x.add(_mgrId,gbc);
		gbc.gridy=3;x.add(_pass,gbc);

		Button save=new Button("Save");
		Button exit=new Button("Exit");
		Button ok=new Button("OK");
		ok.setSize(100,100);

		gbc.gridy=5;gbc.gridx=0;
		gbc.gridwidth=1;
		x.add(exit,gbc);
		gbc.gridx=1;
		x.add(save,gbc);
		save.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent ac){
					dia = new Dialog(adm_frame, "Attention !", true);
					dia.setLayout(new GridLayout(2,1));
					dia.setSize(600,300);
					try{rs=stmt.executeQuery("select e.EmpID from EMPLOYEE e,DEPARTMENT d where (e.EmpID=d.ManagerID && e.EmpID='"+_mgrId.getText()+"') ");
						if(rs.next()){error="Employee is already Manager";
							throw new IOException();}
						rs1=stmt1.executeQuery("select EmpID from EMPLOYEE where EmpID='"+_mgrId.getText()+"'");
						if(!rs1.next()){error="No such Employee exists";
										throw new  IOException();}
						error="Duplicate ID for Department";
						ps = con.prepareStatement("insert into DEPARTMENT values(?,?,?)");
						ps.setString(1,_name.getText());
						ps.setString(2,_depttId.getText());
						ps.setString(3,_mgrId.getText());
						
						int j=ps.executeUpdate();
						ps=con.prepareStatement("insert into USERS values(?,?,?) ");
						ps.setString(1,_mgrId.getText());
						ps.setString(2,_pass.getText());
						ps.setString(3,"Employee");
						j=ps.executeUpdate();

						ps=con.prepareStatement("UPDATE EMPLOYEE set DepttID='"+_depttId.getText()+"' where EmpID='"+_mgrId.getText()+"'");
						j=ps.executeUpdate();
						dia.add(new Label("Succeed !"));
						dia.add(ok);
						dia.setVisible(true);
						System.out.println("success");
						_name.setText(""); mgrId.setText("");_pass.setText("");_depttId.setText("");
						
						}
						catch(Exception ex){dia.add(new Label(error));
											System.out.println(error);
											System.out.println(ex);
											dia.add(ok);
											dia.setVisible(true);
											error="";}
					}});
		ok.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){dia.setVisible(false);}});

		exit.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			Frame f=(Frame)(x.getParent());
			card.show(f,"adm_home_panel");
			}});
		}
	
	public static void setupAdm_new_emp_panel(Panel x){
		x.setLayout(new GridBagLayout());
		GridBagConstraints gbc=new GridBagConstraints();
		gbc.gridx=0;gbc.gridy=0;gbc.gridwidth=1;gbc.gridheight=1;gbc.anchor=GridBagConstraints.CENTER;
		gbc.weightx=4;gbc.weighty=1;

		x.setFont(new Font("Serif",Font.BOLD,15));
		
		Label name=new Label("Name");
		Label empId=new Label("Employee ID");
		Label salary=new Label("Salary");
		Label gender=new Label("Gender");
		Label deptt=new Label("Department ID");
		Label dob=new Label("DOB (yyyy-mm-dd)");
		Label contact=new Label("Contact No");
		TextField _name=new TextField(30);
		TextField _empId=new TextField(30);
		TextField _salary=new TextField(30);
		TextField _gender=new TextField(30);
		TextField _deptt=new TextField(30);
		TextField _dob=new TextField(30);
		TextField _contact=new TextField(30);		

		gbc.gridy=0;x.add(name,gbc);
		gbc.gridy=2;x.add(empId,gbc);
		gbc.gridy=3;x.add(salary,gbc);
		gbc.gridy=4;x.add(gender,gbc);
		gbc.gridy=5;x.add(deptt,gbc);
		gbc.gridy=6;x.add(dob,gbc);
		gbc.gridy=1;x.add(contact,gbc);
		gbc.gridx=1;
		gbc.gridy=0;x.add(_name,gbc);
		gbc.gridy=2;x.add(_empId,gbc);
		gbc.gridy=3;x.add(_salary,gbc);
		gbc.gridy=4;x.add(_gender,gbc);
		gbc.gridy=5;x.add(_deptt,gbc);
		gbc.gridy=6;x.add(_dob,gbc);
		gbc.gridy=1;x.add(_contact,gbc);

		Button save=new Button("Save");
		Button exit=new Button("Exit");
		Button ok=new Button("OK");
		ok.setSize(100,100);
		  
		gbc.gridy=8;gbc.gridx=0;
		gbc.gridwidth=1;
		x.add(exit,gbc);
		gbc.gridx=1;
		x.add(save,gbc);
		save.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent ac){
					dia = new Dialog(adm_frame, "Attention !", true);
					dia.setLayout(new GridLayout(2,1));
					dia.setSize(300,200);
					error="";
					try{rs=stmt.executeQuery("select EmpID from EMPLOYEE where EmpID='"+_empId.getText()+"'");
						if(rs.next()){throw new IOException(error="Duplicate EmployeeID");}
						while(rs.next()){}
						rs1=stmt1.executeQuery("select DepttID from DEPARTMENT where DepttID='"+_deptt.getText()+"'");
						if(!rs1.next()){throw new IOException(error="No such department");}
						while(rs.next()){}

						try{
							if(Integer.parseInt(_salary.getText())<0) throw new IOException("");

						}catch(Exception aaa){
							error=" Invalid  Salary";
							throw new IOException();}

						ps = con.prepareStatement("insert into EMPLOYEE values(?,?,?,?,?,?,?,?)");
						ps.setString(1,_name.getText());
						ps.setString(2,date+"");
						ps.setString(3,_empId.getText());
						ps.setString(4,_salary.getText());
						ps.setString(5,_gender.getText());
						ps.setString(6,_deptt.getText());
						ps.setString(7,_dob.getText());
						ps.setString(8,_contact.getText());
						
						int j=ps.executeUpdate();
						dia.add(new Label("Succeed !"));
						dia.add(ok);
						dia.setVisible(true);
						_empId.setText(""); _name.setText("");  _salary.setText("");
						_gender.setText(""); _deptt.setText(""); _dob.setText(""); _contact.setText(""); 

						}
						catch(Exception ex){dia.add(new Label("Falied !"+error));
											dia.add(ok);
											dia.setVisible(true);}
					}});
		ok.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){dia.setVisible(false);}});

		exit.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			Frame f=(Frame)(x.getParent());
			card.show(f,"adm_home_panel");
			}});
	
		}
	
	public static void setupAdm_edit_emp_panel(Panel x){
		x.setLayout(new GridBagLayout());
		GridBagConstraints gbc=new GridBagConstraints();
		gbc.gridx=0;gbc.gridy=0;gbc.gridwidth=1;gbc.gridheight=1;gbc.anchor=GridBagConstraints.CENTER;
		gbc.weightx=4;gbc.weighty=1;
		x.setFont(new Font("Serif",Font.BOLD,15));
		Label name=new Label("Name");
		Label doj=new Label("Date of Joining");
		Label empId=new Label("Employee ID");
		Label salary=new Label("Salary");
		Label gender=new Label("Gender");
		Label deptt=new Label("DepartmentID");
		Label dob=new Label("Date Of Birth");
		Label contact=new Label("Contact No");
		Label password=new Label("User Password");

		TextField _name=new TextField(30);
		TextField _doj=new TextField(30);
		_doj.setEditable(false);
		TextField _empId=new TextField(30);
		TextField _salary=new TextField(30);
		TextField _gender=new TextField(30);
		_gender.setEditable(false);
		TextField _deptt=new TextField(30);
		TextField _dob=new TextField(30);
		_dob.setEditable(false);
		TextField _contact=new TextField(30);
		TextField _password=new TextField(30);
		
		gbc.gridy=0;x.add(empId,gbc);
		gbc.gridy=1;x.add(name,gbc);
		gbc.gridy=2;x.add(doj,gbc);
		gbc.gridy=3;x.add(salary,gbc);
		gbc.gridy=4;x.add(gender,gbc);
		gbc.gridy=5;x.add(deptt,gbc);
		gbc.gridy=6;x.add(dob,gbc);
		gbc.gridy=7;x.add(contact,gbc);
		
		gbc.gridx=1;
		gbc.gridy=0;x.add(_empId,gbc);
		gbc.gridy=1;x.add(_name,gbc);
		gbc.gridy=2;x.add(_doj,gbc);
		gbc.gridy=3;x.add(_salary,gbc);
		gbc.gridy=4;x.add(_gender,gbc);
		gbc.gridy=5;x.add(_deptt,gbc);
		gbc.gridy=6;x.add(_dob,gbc);
		gbc.gridy=7;x.add(_contact,gbc);
		gbc.gridy=8;
		gbc.gridx=0;x.add(password,gbc);
		gbc.gridx=1;x.add(_password,gbc);
		password.setVisible(false);
		_password.setVisible(false);

		Button save=new Button("Save");
		Button exit=new Button("Exit");
		Button  ok=new Button("OK");
		Button  _ok=new Button("OK");
		//save.setSize()
		gbc.gridx=2;
		gbc.gridy=0;
		x.add(ok,gbc);

		gbc.gridy=9;
		gbc.gridx=0;
		gbc.gridwidth=1;
		x.add(save,gbc);
		gbc.gridx=1;
		x.add(exit,gbc);
		save.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent ac){
					dia = new Dialog(adm_frame, "Attention !", true);
					dia.setLayout(new GridLayout(2,1));
					dia.setSize(300,200);
					error="";
					try{
						rs1=stmt1.executeQuery("select DepttID from DEPARTMENT where DepttID='"+_deptt.getText()+"'");
						if(!rs1.next()){throw new IOException(error="No such department");}
						while(rs.next()){}

						try{
							if(Integer.parseInt(_salary.getText())<0) throw new IOException("");

						}catch(Exception aaa){
							error=" Invalid  Salary";
							throw new IOException();}
						
						ps = con.prepareStatement("update EMPLOYEE set Salary=?,Contact=?,Name=?,DepttID=? where EmpID='"+_empId.getText()+"'");
						ps.setString(1,_salary.getText());
						ps.setString(2,_contact.getText());
						ps.setString(3,_name.getText());
						ps.setString(4,_deptt.getText());
						
						int j=ps.executeUpdate();
						ps = con.prepareStatement("update USERS set Password=? where UserID='"+_empId.getText()+"'");
						ps.setString(1,_password.getText());

						j=ps.executeUpdate();
						dia.add(new Label("Succeed !"));
						dia.add(ok);
						dia.setVisible(true);
						}catch(Exception ex){dia.add(new Label("Falied !"+error));
											dia.add(_ok);
											dia.setVisible(true);}
					}});
		_ok.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){dia.setVisible(false);}});

		ok.addActionListener(new  ActionListener(){
			public void actionPerformed(ActionEvent e){
			try{
				rs=stmt.executeQuery("select * from EMPLOYEE where EmpID='"+_empId.getText()+"'");
				rs1=stmt1.executeQuery("select Password from USERS where UserID='"+_empId.getText()+"'");
		//System.out.println("gonna print rs \n"+rs);
		//System.out.println(" "+user_tf.getText());
				while(rs.next() ){
		
					_name.setText(rs.getString(1));
					try{_doj.setText(rs.getString(2)); }catch(Exception exx){}    
					_empId.setText(rs.getString(3));
					_salary.setText(rs.getString(4));
					_gender.setText(rs.getString(5));
					_deptt.setText(rs.getString(6));
					try{_dob.setText(rs.getString(7));}catch(Exception ex){}
					_contact.setText(rs.getString(8));
						}		
				if(rs1.next()){
					password.setVisible(true);
					_password.setVisible(true);
					_password.setText(rs1.getString(1));
				}
				else{
					password.setVisible(false);
					_password.setVisible(false);
				}
				rs=stmt.executeQuery("select UserID from USERS where UserID='"+_empId.getText()+"'");
				if(rs.next())_deptt.setEditable(false);
				else _deptt.setEditable(true);

						}catch (Exception ex){System.out.println(ex);}
				
					}
				});
		
		exit.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			Frame f=(Frame)(x.getParent());
			card.show(f,"emp_home_panel");
			}});
		}
	
	public static void setupAdm_edit_deptt_panel(Panel x){
		x.setLayout(new GridBagLayout());
		GridBagConstraints gbc=new GridBagConstraints();
		gbc.gridx=0;gbc.gridy=0;gbc.gridwidth=1;gbc.gridheight=1;gbc.anchor=GridBagConstraints.CENTER;
		gbc.weightx=4;gbc.weighty=1;
		
		x.setFont(new Font("Serif",Font.BOLD,15));
		
		Label name=new Label("Name");
		Label mgrId=new Label("Manager ID");
		Label depttId=new Label("Department ID");
		Label pass=new Label("Manager Password");
		TextField _name=new TextField(30);
		TextField _depttId=new TextField(30);
		TextField _mgrId=new TextField(30);
		TextField _pass=new TextField(30);

		gbc.gridy=0;x.add(depttId,gbc);
		gbc.gridy=1;x.add(name,gbc);
		gbc.gridy=2;x.add(mgrId,gbc);
		gbc.gridy=3;x.add(pass,gbc);
		
		gbc.gridx=1;
		gbc.gridy=0;x.add(_depttId,gbc);
		gbc.gridy=1;x.add(_name,gbc);
		gbc.gridy=2;x.add(_mgrId,gbc);
		gbc.gridy=3;x.add(_pass,gbc);

		Button save=new Button("Save");
		Button exit=new Button("Exit");
		Button ok=new Button("OK");
		Button go=new Button("OK");
		ok.setSize(100,100);

		gbc.gridx=2;gbc.gridy=0;x.add(go,gbc);

		gbc.gridy=5;gbc.gridx=0;
		gbc.gridwidth=1;
		x.add(exit,gbc);
		gbc.gridx=1;
		x.add(save,gbc);
		save.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent ac){
					dia = new Dialog(adm_frame, "Attention !", true);
					dia.setLayout(new GridLayout(2,1));
					dia.setSize(600,300);
					try{rs=stmt.executeQuery("select e.EmpID from EMPLOYEE e,DEPARTMENT d where (e.EmpID=d.ManagerID && e.EmpID='"+_mgrId.getText()+"' && d.DepttID <>'"+_depttId.getText()+"') ");
						if(rs.next()){error="Employee is already Manager";
							throw new IOException();}
						rs1=stmt1.executeQuery("select EmpID from EMPLOYEE where EmpID='"+_mgrId.getText()+"'");
						if(!rs1.next()){error="No such Employee exists";
										throw new  IOException();}
						//error="Duplicate ID for Department";
						ps = con.prepareStatement("update  DEPARTMENT set Name=?,ManagerID=? where DepttID='"+_depttId.getText()+"'");
						ps.setString(1,_name.getText());
						ps.setString(2,_mgrId.getText());
						
						int j=ps.executeUpdate();
						rs=stmt.executeQuery("select UserID from USERS where UserID='"+_mgrId.getText()+"'");
						if(rs.next()){
						ps=con.prepareStatement("UPDATE USERS set Password=? where UserID='"+_mgrId.getText()+"'");
						//ps.setString(1,_mgrId.getText());
						ps.setString(1,_pass.getText());
					//	ps.setString(3,"Employee");
						j=ps.executeUpdate();
							}
						else{
							ps=con.prepareStatement("insert into USERS values(?,?,?)");
							ps.setString(1,_mgrId.getText());
							ps.setString(2,_pass.getText());
							ps.setString(3,"Employee");
							j=ps.executeUpdate();
							ps=con.prepareStatement("delete from USERS where UserID ='"+currm+"'");
						}
						ps=con.prepareStatement("UPDATE EMPLOYEE set DepttID='"+_depttId.getText()+"' where EmpID='"+_mgrId.getText()+"'");
						j=ps.executeUpdate();
						dia.add(new Label("Succeed !"));
						dia.add(ok);
						dia.setVisible(true);
						System.out.println("success-----");
						}
						catch(Exception ex){dia.add(new Label(error));
											System.out.println(error);
											System.out.println(ex);
											dia.add(ok);
											dia.setVisible(true);
											error="";}
					}});
		ok.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){dia.setVisible(false);}});

		go.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
					rs=stmt.executeQuery("select Name,ManagerID from DEPARTMENT where DepttID='"+_depttId.getText()+"'");
					while(rs.next()){
						_mgrId.setText(rs.getString(2));
						_name.setText(rs.getString(1));
						currm=rs.getString(2);
					}
					rs1=stmt1.executeQuery("select Password from USERS where UserID='"+_mgrId.getText()+"'");
					while(rs1.next())_pass.setText(rs1.getString(1));
				}catch(Exception ex){System.out.println(ex);}
			}
		});		

		exit.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			Frame f=(Frame)(x.getParent());
			card.show(f,"adm_home_panel");
			}});}	

	public static void setupAdm_edit_prod_panel(Panel x){
		x.setLayout(new GridBagLayout());
		GridBagConstraints gbc=new GridBagConstraints();
		gbc.gridx=0;gbc.gridy=0;gbc.gridwidth=1;gbc.gridheight=1;gbc.anchor=GridBagConstraints.CENTER;
		gbc.weightx=4;gbc.weighty=1;

		x.setFont(new Font("Serif",Font.BOLD,15));
		
		Label name=new Label("Name");
		Label prodId=new Label("Product ID");
		Label price=new Label("Price");
		Label cost=new Label("Cost");
		Label deptt=new Label("Department ID");
		Label suppID=new Label("Supplier  ID");
		Label brand=new Label("Brand");
		Label quant=new Label("Stock");
		TextField _name=new TextField(30);
		_name.setEditable(false);
		TextField _prodId=new TextField(30);
		TextField _price=new TextField(30);
		TextField _cost=new TextField(30);
		//_cost.setEditable(false);
		TextField _deptt=new TextField(30);
		TextField _suppID=new TextField(30);		
		TextField _brand=new TextField(30);
		TextField _quant=new TextField(30);
		_quant.setEditable(false);
		_brand.setEditable(false);
		
		gbc.gridy=0;x.add(prodId,gbc);
		gbc.gridy=1;x.add(name,gbc);
		gbc.gridy=2;x.add(suppID,gbc);
		gbc.gridy=3;x.add(price,gbc);
		gbc.gridy=4;x.add(cost,gbc);
		gbc.gridy=5;x.add(deptt,gbc);
		gbc.gridy=6;x.add(brand,gbc);
		gbc.gridy=7;x.add(quant,gbc);
		gbc.gridx=1;
		gbc.gridy=0;x.add(_prodId,gbc);
		gbc.gridy=1;x.add(_name,gbc);
		gbc.gridy=2;x.add(_suppID,gbc);
		gbc.gridy=3;x.add(_price,gbc);
		gbc.gridy=4;x.add(_cost,gbc);
		gbc.gridy=5;x.add(_deptt,gbc);
		gbc.gridy=6;x.add(_brand,gbc);
		gbc.gridy=7;x.add(_quant,gbc);

		Button save=new Button("Save");
		Button exit=new Button("Exit");
		Button ok=new Button("OK");
		Button go=new Button("OK");
		ok.setSize(100,100);
		  
		gbc.gridy=0;gbc.gridx=2;x.add(go,gbc);
		gbc.gridy=9;gbc.gridx=0;
		gbc.gridwidth=1;
		x.add(exit,gbc);
		gbc.gridx=1;
		x.add(save,gbc);
		save.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent ac){
					dia = new Dialog(adm_frame, "Attention !", true);
					dia.setLayout(new GridLayout(2,1));
					dia.setSize(300,200);
					try{
					rs=stmt.executeQuery("select DepttID from DEPARTMENT where DepttID='"+_deptt.getText()+"'");
						if(!rs.next()){
							dia.add(new Label("Falied ! Unknown Department ID "));
							throw new IOException();}
					while(rs.next()){};
					rs=stmt.executeQuery("select SuppID from SUPPLIER where SuppID='"+_suppID.getText()+"'");
						if(!rs.next()){
							dia.add(new Label("Falied ! Unknown Supplier ID "));
							throw new IOException();}
					while(rs.next()){};
					try{
						if(Integer.parseInt(_price.getText())<0) throw new IOException("");

					}catch(Exception aaa){dia.add(new Label("Falied ! Invalid  Price "));
							throw new IOException();}
					try{
						if(Integer.parseInt(_cost.getText())<0) throw new IOException("");

					}catch(Exception aa){dia.add(new Label("Falied ! Invalid Cost "));
							throw new IOException();}
						ps = con.prepareStatement("update PRODUCT set Price=?,SuppID=?,DepttID=?,Cost=? where ProdID='"+_prodId.getText()+"'");
						ps.setString(3,_deptt.getText());
						ps.setString(1,_price.getText());
						ps.setString(2,_suppID.getText());
						ps.setString(4,_cost.getText());
						
						int j=ps.executeUpdate();
						dia.add(new Label("Succeed !"));
						dia.add(ok);
						dia.setVisible(true);
						}
						catch(Exception ex){
											dia.add(ok);
											dia.setVisible(true);}
					}});
		ok.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){dia.setVisible(false);}});

		go.addActionListener(new  ActionListener(){
			public void  actionPerformed(ActionEvent e){
				try{
				rs=stmt.executeQuery("select * from PRODUCT where ProdID='"+_prodId.getText()+"'");
						while(rs.next()){
							_name.setText(rs.getString(1));
							_deptt.setText(rs.getString(3));
							_price.setText(rs.getString(4));
							_brand.setText(rs.getString(5));
							_quant.setText(rs.getString(6));
							_suppID.setText(rs.getString(7));
							_cost.setText(rs.getString(8));
						}
					}catch (Exception ex) {System.out.println(ex);}
				}
			});

		exit.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			Frame f=(Frame)(x.getParent());
			card.show(f,"adm_home_panel");
			}});
		}
	
	public static void setupAdm_delete_emp_panel(Panel x){
		x.setLayout(new GridBagLayout());
		GridBagConstraints gbc=new GridBagConstraints();
		gbc.gridx=0;gbc.gridy=0;gbc.gridwidth=1;gbc.gridheight=1;gbc.anchor=GridBagConstraints.CENTER;
		gbc.weightx=4;gbc.weighty=1;
		x.setFont(new Font("Serif",Font.BOLD,15));
		Label name=new Label("Name");
		Label doj=new Label("Date of Joining");
		Label empId=new Label("Employee ID");
		Label salary=new Label("Salary");
		Label gender=new Label("Gender");
		Label deptt=new Label("DepartmentID");
		Label dob=new Label("Date Of Birth");
		Label contact=new Label("Contact No");
		Label password=new Label("User Password");

		TextField _name=new TextField(30);
		_name.setEditable(false);
		TextField _doj=new TextField(30);
		_doj.setEditable(false);
		TextField _empId=new TextField(30);
		TextField _salary=new TextField(30);
		_salary.setEditable(false);
		TextField _gender=new TextField(30);
		_gender.setEditable(false);
		TextField _deptt=new TextField(30);
		_deptt.setEditable(false);
		TextField _dob=new TextField(30);
		_dob.setEditable(false);
		TextField _contact=new TextField(30);
		_contact.setEditable(false);
		TextField _password=new TextField(30);
		_password.setEditable(false);
		
		gbc.gridy=0;x.add(empId,gbc);
		gbc.gridy=1;x.add(name,gbc);
		gbc.gridy=2;x.add(doj,gbc);
		gbc.gridy=3;x.add(salary,gbc);
		gbc.gridy=4;x.add(gender,gbc);
		gbc.gridy=5;x.add(deptt,gbc);
		gbc.gridy=6;x.add(dob,gbc);
		gbc.gridy=7;x.add(contact,gbc);
		 
		gbc.gridx=1;
		gbc.gridy=0;x.add(_empId,gbc);
		gbc.gridy=1;x.add(_name,gbc);
		gbc.gridy=2;x.add(_doj,gbc);
		gbc.gridy=3;x.add(_salary,gbc);
		gbc.gridy=4;x.add(_gender,gbc);
		gbc.gridy=5;x.add(_deptt,gbc);
		gbc.gridy=6;x.add(_dob,gbc);
		gbc.gridy=7;x.add(_contact,gbc);
		gbc.gridy=8;
		gbc.gridx=0;x.add(password,gbc);
		gbc.gridx=1;x.add(_password,gbc);
		password.setVisible(false);
		_password.setVisible(false);

		Button delete=new Button("Delete");
		Button exit=new Button("Exit");
		Button  go=new Button("OK");
		Button  ok=new Button("OK");
		gbc.gridx=2;
		gbc.gridy=0;
		x.add(go,gbc);

		gbc.gridy=9;
		gbc.gridx=0;
		gbc.gridwidth=1;
		x.add(delete,gbc);
		gbc.gridx=1;
		x.add(exit,gbc);
		delete.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent ac){
					try{
						//dia.new Dialog(adm_frame,"Attention !",true);
						dia = new Dialog(adm_frame, "Attention !", true);
						dia.setLayout(new GridBagLayout());
						dia.setSize(600,200);
						ps = con.prepareStatement("delete from EMPLOYEE where EmpID='"+_empId.getText()+"'");
						
						int j=ps.executeUpdate();
						gbc.gridx=0;gbc.gridy=0;
						dia.add(new Label("Succeed !"),gbc);
						gbc.gridy=1;dia.add(ok,gbc);
						dia.setVisible(true);
						_empId.setText(""); _name.setText(""); _doj.setText(""); _salary.setText("");
						_gender.setText(""); _deptt.setText(""); _dob.setText(""); _contact.setText(""); _password.setText("");

						}catch(Exception ex){gbc.gridx=0;gbc.gridy=0;
											dia.add(new Label("failed !"),gbc);
											gbc.gridy=1;dia.add(ok,gbc);
											dia.setVisible(true);
											System.out.println(ex);}
					}});

		go.addActionListener(new  ActionListener(){
			public void actionPerformed(ActionEvent e){
			try{
				rs=stmt.executeQuery("select * from EMPLOYEE where EmpID='"+_empId.getText()+"'");
				rs1=stmt1.executeQuery("select Password from USERS where UserID='"+_empId.getText()+"'");

				while(rs.next() ){
		
					_name.setText(rs.getString(1));
					try{_doj.setText(rs.getString(2)); }catch(Exception exx){}    
					_empId.setText(rs.getString(3));
					_salary.setText(rs.getString(4));
					_gender.setText(rs.getString(5));
					_deptt.setText(rs.getString(6));
					try{_dob.setText(rs.getString(7));}catch(Exception ex){}
					_contact.setText(rs.getString(8));
						}		
				if(rs1.next()){
					password.setVisible(true);
					_password.setVisible(true);
					delete.setVisible(false);
					_password.setText(rs1.getString(1));
				}
				else{
					password.setVisible(false);
					_password.setVisible(false);
					delete.setVisible(true);
				}
				

						}catch (Exception ex){System.out.println(ex);}
				
					}
				});
		ok.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){dia.setVisible(false);}});
		exit.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			Frame f=(Frame)(x.getParent());
			card.show(f,"adm_home_panel");
			}});

		}
	
	public static void setupAdm_delete_prod_panel(Panel x){
		x.setLayout(new GridBagLayout());
		GridBagConstraints gbc=new GridBagConstraints();
		gbc.gridx=0;gbc.gridy=0;gbc.gridwidth=1;gbc.gridheight=1;gbc.anchor=GridBagConstraints.CENTER;
		gbc.weightx=4;gbc.weighty=1;

		x.setFont(new Font("Serif",Font.BOLD,15));
		
		Label name=new Label("Name");
		Label prodId=new Label("Product ID");
		Label price=new Label("Price");
		Label cost=new Label("Cost");
		Label deptt=new Label("Department ID");
		Label suppID=new Label("Supplier  ID");
		Label brand=new Label("Brand");
		Label quant=new Label("Stock");
		TextField _name=new TextField(30);
		_name.setEditable(false);
		TextField _prodId=new TextField(30);
		TextField _price=new TextField(30);
		_price.setEditable(false);
		TextField _cost=new TextField(30);
		_cost.setEditable(false);
		TextField _deptt=new TextField(30);
		_deptt.setEditable(false);
		TextField _suppID=new TextField(30);
		_suppID.setEditable(false);		
		TextField _brand=new TextField(30);
		_brand.setEditable(false);
		TextField _quant=new TextField(30);
		_quant.setEditable(false);
		 
		gbc.gridy=0;x.add(prodId,gbc);
		gbc.gridy=1;x.add(name,gbc);
		gbc.gridy=2;x.add(suppID,gbc);
		gbc.gridy=3;x.add(price,gbc);
		gbc.gridy=4;x.add(cost,gbc);
		gbc.gridy=5;x.add(deptt,gbc);
		gbc.gridy=6;x.add(brand,gbc);
		gbc.gridy=7;x.add(quant,gbc);
		gbc.gridx=1;
		gbc.gridy=0;x.add(_prodId,gbc);
		gbc.gridy=1;x.add(_name,gbc);
		gbc.gridy=2;x.add(_suppID,gbc);
		gbc.gridy=3;x.add(_price,gbc);
		gbc.gridy=4;x.add(_cost,gbc);
		gbc.gridy=5;x.add(_deptt,gbc);
		gbc.gridy=6;x.add(_brand,gbc);
		gbc.gridy=7;x.add(_quant,gbc);

		Button delete=new Button("Delete");
		Button exit=new Button("Exit");
		Button ok=new Button("OK");
		Button go=new Button("OK");
		ok.setSize(100,100);
		  
		gbc.gridy=0;gbc.gridx=2;x.add(go,gbc);
		gbc.gridy=9;gbc.gridx=0;
		gbc.gridwidth=1;
		x.add(exit,gbc);
		gbc.gridx=1;
		x.add(delete,gbc);
		Button delete_ok=new Button("Delete");
		Button delete_cancel=new Button("Cancel");
		
		cdia=new Dialog(adm_frame,"Attention !",true);
		cdia.setLayout(new GridBagLayout());
		gbc.gridy=gbc.gridx=0;gbc.gridwidth=2;
		cdia.add(new Label("The Product will be deleted Entirely form database!"),gbc);
		gbc.gridy=1;
		cdia.add(new Label("The Product will be deleted from Sales and Orders!"),gbc);
		gbc.gridy=2;gbc.gridwidth=1;
		cdia.add(delete_cancel,gbc);
		gbc.gridx=1;cdia.add(delete_ok,gbc);
		cdia.setSize(600,300);

		delete.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){cdia.setVisible(true);}});
		delete_cancel.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){cdia.setVisible(false);}});
		delete_ok.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent ac){
					cdia.setVisible(false);
					dia = new Dialog(adm_frame, "Attention !", true);
					dia.setLayout(new GridBagLayout());
					dia.setSize(600,200);
					try{
						ps1=con.prepareStatement("UPDATE SALES_RECORD set ProdID='Unknown' where ProdID='"+_prodId.getText()+"'");
						int j=ps1.executeUpdate();
						ps1=con.prepareStatement("UPDATE _ORDER set ProdID='Unknown' where ProdID='"+_prodId.getText()+"'");
						j=ps1.executeUpdate();
						
						ps = con.prepareStatement("delete from PRODUCT where ProdID='"+_prodId.getText()+"'");

						j=ps.executeUpdate();
						gbc.gridx=0;gbc.gridy=0;
						dia.add(new Label("Succeed !"),gbc);
						gbc.gridy=1;dia.add(ok,gbc);
						dia.setVisible(true);
						_name.setText("");_price.setText(""); _prodId.setText(""); _cost.setText("");
						_deptt.setText(""); _suppID.setText(""); _brand.setText(""); _quant.setText("");
						}
						catch(Exception ex){gbc.gridx=0;gbc.gridy=0;
											dia.add(new Label("failed !"),gbc);
											gbc.gridy=1;dia.add(ok,gbc);
											dia.setVisible(true);
											System.out.println(ex);}
					}});
		ok.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){dia.setVisible(false);}});

		go.addActionListener(new  ActionListener(){
			public void  actionPerformed(ActionEvent e){
				try{
				rs=stmt.executeQuery("select * from PRODUCT where ProdID='"+_prodId.getText()+"'");
						while(rs.next()){
							_name.setText(rs.getString(1));
							_deptt.setText(rs.getString(3));
							_price.setText(rs.getString(4));
							_brand.setText(rs.getString(5));
							_quant.setText(rs.getString(6));
							_suppID.setText(rs.getString(7));
							_cost.setText(rs.getString(8));
						}
					}catch (Exception ex) {System.out.println(ex);}
				}
			});

		exit.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			Frame f=(Frame)(x.getParent());
			card.show(f,"adm_home_panel");
			}});
		
		}
	
	public static void setupAdm_delete_deptt_panel(Panel x){
		x.setLayout(new GridBagLayout());
		GridBagConstraints gbc=new GridBagConstraints();
		gbc.gridx=0;gbc.gridy=0;gbc.gridwidth=1;gbc.gridheight=1;gbc.anchor=GridBagConstraints.CENTER;
		gbc.weightx=4;gbc.weighty=1;

		x.setFont(new Font("Serif",Font.BOLD,15));
		
		Label name=new Label("Name");
		Label mgrId=new Label("Manager ID");
		Label depttId=new Label("Department ID");
		Label pass=new Label("Manager Password");
		TextField _name=new TextField(30);
		TextField _depttId=new TextField(30);
		TextField _mgrId=new TextField(30);
		TextField _pass=new TextField(30);

		gbc.gridy=0;x.add(depttId,gbc);
		gbc.gridy=1;x.add(name,gbc);
		gbc.gridy=2;x.add(mgrId,gbc);
		gbc.gridy=3;x.add(pass,gbc);
		
		gbc.gridx=1;
		gbc.gridy=0;x.add(_depttId,gbc);
		gbc.gridy=1;x.add(_name,gbc);
		_name.setEditable(false);
		gbc.gridy=2;x.add(_mgrId,gbc);
		_mgrId.setEditable(false);
		gbc.gridy=3;x.add(_pass,gbc);
		_pass.setEditable(false);
		
		Button delete=new Button("Delete");
		Button exit=new Button("Exit");
		Button ok=new Button("OK");
		Button go=new Button("OK");
		ok.setSize(100,100);
		  
		gbc.gridy=0;gbc.gridx=2;x.add(go,gbc);
		gbc.gridy=5;gbc.gridx=0;
		gbc.gridwidth=1;
		x.add(exit,gbc);
		gbc.gridx=1;
		x.add(delete,gbc);
		Button delete_ok=new Button("Delete");
		Button delete_cancel=new Button("Cancel");
		
		cdia=new Dialog(adm_frame,"Attention !",true);
		cdia.setLayout(new GridBagLayout());
		gbc.gridy=gbc.gridx=0;gbc.gridwidth=2;
		cdia.add(new Label("The Products of Department will be deleted Entirely form database!"),gbc);
		gbc.gridy=1;
		cdia.add(new Label("The Employees of Department will be deleted from Sales and Orders!"),gbc);
		gbc.gridy=2;gbc.gridwidth=1;
		cdia.add(delete_cancel,gbc);
		gbc.gridx=1;cdia.add(delete_ok,gbc);
		cdia.setSize(600,300);

		delete.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){cdia.setVisible(true);}});
		delete_cancel.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){cdia.setVisible(false);}});
		delete_ok.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent ac){
					cdia.setVisible(false);
					dia = new Dialog(adm_frame, "Attention !", true);
					dia.setLayout(new GridBagLayout());
					dia.setSize(600,200);
					try{
						
						ps = con.prepareStatement("delete from USERS where UserID='"+_mgrId.getText()+"'");
						int j=ps.executeUpdate();
						ps = con.prepareStatement("delete from EMPLOYEE where DepttID='"+_depttId.getText()+"'");
						j=ps.executeUpdate();
						rs=stmt1.executeQuery("select ProdID from PRODUCT where DepttID='"+_depttId.getText()+"'");
						while(rs.next()){

						ps1=con.prepareStatement("UPDATE SALES_RECORD set ProdID='Unknown' where ProdID='"+rs.getString(1)+"'");
						j=ps1.executeUpdate();
						ps1=con.prepareStatement("UPDATE _ORDER set ProdID='Unknown' where ProdID='"+rs.getString(1)+"'");
						j=ps1.executeUpdate();
						
						ps = con.prepareStatement("delete from PRODUCT where ProdID='"+rs.getString(1)+"'");

						j=ps.executeUpdate();
						}
						gbc.gridx=0;gbc.gridy=0;
						dia.add(new Label("Succeed !"),gbc);
						gbc.gridy=1;dia.add(ok,gbc);
						dia.setVisible(true);
						_name.setText(""); mgrId.setText("");_name.setText("");_pass.setText("");_depttId.setText("");
						}
						catch(Exception ex){gbc.gridx=0;gbc.gridy=0;
											dia.add(new Label("failed !"),gbc);
											gbc.gridy=1;dia.add(ok,gbc);
											dia.setVisible(true);
											System.out.println(ex);}
					}});
		ok.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){dia.setVisible(false);}});

		go.addActionListener(new  ActionListener(){
			public void  actionPerformed(ActionEvent e){
				try{
				rs=stmt.executeQuery("select d.Name ,d.ManagerID,u.Password from DEPARTMENT d,USERS u where d.DepttID='"+_depttId.getText()+"' && u.UserID=d.ManagerID");
						while(rs.next()){
							_name.setText(rs.getString(1));
							_mgrId.setText(rs.getString(2));
							_pass.setText(rs.getString(3));
							
						}
					}catch (Exception ex) {System.out.println(ex);}
				}
			});

		exit.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			Frame f=(Frame)(x.getParent());
			card.show(f,"adm_home_panel");
			}});
		
		
		}
	
	public static void setupAdm_delete_supplier_panel(Panel x){

		GridBagConstraints gbc=new GridBagConstraints();
		gbc.gridx=0;gbc.gridy=0;gbc.gridwidth=1;gbc.gridheight=1;gbc.anchor=GridBagConstraints.CENTER;
		gbc.weightx=4;gbc.weighty=1;

		x.setFont(new Font("Serif",Font.BOLD,15));
		
		Label name=new Label("Name");
		Label suppId=new Label("Supplier ID");
		Label gender=new Label("Gender");
		Label contact=new Label("Contact");
		TextField _name=new TextField(30);
		TextField _suppId=new TextField(30);
		TextField _gender=new TextField(30);
		TextField _contact=new TextField(30);

		gbc.gridy=1;x.add(name,gbc);
		gbc.gridy=0;x.add(suppId,gbc);
		gbc.gridy=2;x.add(gender,gbc);
		gbc.gridy=3;x.add(contact,gbc);
		
		gbc.gridx=1;
		gbc.gridy=1;x.add(_name,gbc);
		_name.setEditable(false);
		gbc.gridy=0;x.add(_suppId,gbc);
		gbc.gridy=2;x.add(_gender,gbc);
		_gender.setEditable(false);
		gbc.gridy=3;x.add(_contact,gbc);
		_contact.setEditable(false);

		Button save=new Button("Delete");
		Button exit=new Button("Exit");
		Button ok=new Button("OK");
		Button go=new Button("OK");
		ok.setSize(100,100);

		gbc.gridy=0;gbc.gridx=2;x.add(go,gbc);
		gbc.gridy=5;gbc.gridx=0;
		gbc.gridwidth=1;
		x.add(exit,gbc);
		gbc.gridx=1;
		x.add(save,gbc);
		save.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent ac){
					dia = new Dialog(adm_frame, "Attention !", true);
					dia.setLayout(new GridBagLayout());
					dia.setSize(600,300);
					try{
						rs=stmt.executeQuery("select * from PRODUCT where SuppID='"+_suppId.getText()+"'");
						if(!rs.next()){

							ps = con.prepareStatement("delete from SUPPLIER where SuppID='"+_suppId.getText()+"'");
						

							int j=ps.executeUpdate();
							gbc.gridx=gbc.gridy=0;
							dia.add(new Label("Succeed !"),gbc);
							gbc.gridy=1;
							dia.add(ok,gbc);
							System.out.println("success");
							dia.setVisible(true);
							_name.setText("");_gender.setText("");_suppId.setText("");_contact.setText("");
						}
						else{gbc.gridx=gbc.gridy=0;
							dia.add(new Label("first change supplier for products supplied by this supplier!"),gbc);
							gbc.gridy=1;
							dia.add(ok,gbc);
							System.out.println("caution");
							dia.setVisible(true);}
					}
						catch(Exception ex){gbc.gridx=gbc.gridy=0;
											dia.add(new Label("Failed !"),gbc);
											gbc.gridy=1;
											dia.add(ok,gbc);
											System.out.println(ex);
											dia.setVisible(true);
											error="";}
							}});
		ok.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){dia.setVisible(false);}});

		go.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
				rs=stmt.executeQuery("select Name,Gender,ContactInfo from SUPPLIER where SuppID='"+_suppId.getText()+"'");
				while(rs.next()){
					_name.setText(rs.getString(1));
					_gender.setText(rs.getString(2));
					_contact.setText(rs.getString(3));
						}
					}catch(Exception ex){System.out.println(ex);}
				}
			});

		exit.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			Frame f=(Frame)(x.getParent());
			card.show(f,"adm_home_panel");
			}});
		
	
		}
	
	public static void setupAdm_view_emp_panel(Panel x){
		x.setFont(new Font("Serif",Font.BOLD,16));
		x.setLayout(new GridBagLayout());
		
		Label adm_view_deptt_selectBy=new Label("  Sort by-");
		Label adm_view_deptt_orderBy=new Label("   Order by-");
		
		Choice c1=new Choice();
		Choice c2=new Choice();
		Choice c3=new Choice();
		c1.addItem("None");
		c1.addItem("Employees ID");c1.addItem(" Name");c1.addItem("Department ID");c1.addItem("Department Name");
		c1.addItem("Joinnig Date");c1.addItem("Salary");c1.addItem("Gender");c1.addItem("Date Of Birth");c1.addItem("Contact");
		c1.addItem("Managers Only");c1.addItem("Not Managers");
		
		c2.add("None");
		c2.addItem("Employees ID");c2.addItem(" Name");c2.addItem("Department ID");c2.addItem("Department Name");
		c2.addItem("Joinnig Date");c2.addItem("Salary");c2.addItem("Gender");c2.addItem("Date Of Birth");c2.addItem("Contact");
		
		c3.add("Ascending");
		c3.add("Descending");

		Button adm_view_deptt_ok=new Button ("  OK  ");
		TextField _adm_view_deptt_selectBy=new TextField(20);

		GridBagConstraints gbc=new GridBagConstraints();
		gbc.gridx=0;gbc.gridy=0;gbc.gridwidth=1;gbc.gridheight=1;gbc.anchor=GridBagConstraints.CENTER;
		gbc.weightx=10;gbc.weighty=1;


		gbc.gridx=0; x.add(adm_view_deptt_selectBy,gbc);
		gbc.gridx=1;x.add(c1,gbc);
		gbc.gridx=2; x.add(_adm_view_deptt_selectBy,gbc);
		gbc.gridx=3; x.add(adm_view_deptt_orderBy,gbc);
		gbc.gridx=4; x.add(c2,gbc);
		gbc.gridx=5; x.add(c3,gbc);
		gbc.gridx=8; x.add(adm_view_deptt_ok,gbc);

		gbc.gridx=0;
		gbc.gridy=1;gbc.gridwidth=10;
		//gbc.weightx=100;
		gbc.fill = GridBagConstraints.BOTH;
		JTable tbl=new JTable(1,1);
		JScrollPane sp=new JScrollPane(tbl);
		sp.setBackground(Color.RED);
		tbl.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		x.add(sp,gbc);
		
		adm_view_deptt_ok.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int sort=c1.getSelectedIndex();
				String value=_adm_view_deptt_selectBy.getText();
				int orderBy=c2.getSelectedIndex();
				int order=c3.getSelectedIndex();
				String query="select e.EmpID,e.Name,e.DepttID,d.name,e.DateOfJoining,e.Salary,e.Gender,e.DoB,e.Contact from EMPLOYEE e, DEPARTMENT d where (e.EmpID<>'Unknown' && e.DepttID=d.DepttID ";
				String orderBy_string=" ";
				String orderType=" ASC";
				String cond=" ) ";
				switch(orderBy){
					case 1:orderBy_string=" order by e.EmpID";break;
					case 2:orderBy_string=" order by e.Name";break;
					case 3:orderBy_string=" order by d.DepttID";break;
					case 4:orderBy_string=" order by d.Name";break;
					case 5:orderBy_string=" order by e.DateOfJoining";break;
					case 6:orderBy_string=" order by e.Salary";break;
					case 7:orderBy_string=" order by e.Gender";break;
					case 8:orderBy_string=" order by e.DoB";break;
					case 9:orderBy_string=" order by e.Contact";break;
					default:orderBy_string=" ";
						
				}
				if(order==1){
					orderType=" DESC";
				}
				switch(sort){
					case 1:cond=" && e.EmpID='"+value+"')"		;break;
					case 2:cond=" && e.Name='"+value+"')"		;break;
					case 3:cond=" && d.DepttID='"+value+"')"	;break;
					case 4:cond=" && d.Name='"+value+"')"		;break;
					case 5:cond=" && e.DateOfJoining='"+value+"')"		;break;
					case 6:cond=" && e.Salary='"+value+"')"		;break;
					case 7:cond=" && e.Gender='"+value+"')"		;break;
					case 8:cond=" && e.DoB='"+value+"')"		;break;
					case 9:cond=" && e.Contact='"+value+"')"		;break;
					default:cond=" )"		;
				}
				if(orderBy_string.equals(" "))orderType="";
				try{
					vector2=new Vector<Vector<String>>();
					int user=0;
					rs=stmt.executeQuery(query+cond+orderBy_string+orderType);
					while(rs.next()){
						rs1=stmt1.executeQuery("select * from USERS where UserID='"+rs.getString(1)+"'");
						vector=new Vector<String>();
						for(int i=1;i<10;i++)vector.add(rs.getString(i));
						if(rs1.isBeforeFirst())
							vector.add("Manager");
						else
							vector.add("Employee");
						if((rs1.isBeforeFirst()&& sort==10)||(sort==11 && !rs1.isBeforeFirst())|| sort<=9)
							vector2.add(vector);
						while(rs1.next()){}
					}
										
					vector=new Vector<String>();
					vector.add("Employee ID");vector.add("Employee Name");vector.add("Department ID");vector.add("Department Name");
					vector.add("DateOfJoining");vector.add("Salary");vector.add("Gender");vector.add("DateOfBirth");vector.add("Contact");vector.add("Post");
					//System.out.println(tbl.getModel().getClass());
					DefaultTableModel dtm=new DefaultTableModel(vector2,vector);
					tbl.setModel(dtm);
					dtm.fireTableDataChanged();
				}catch(Exception eee){eee.printStackTrace();}

				}
			});
		}
	
	public static void setupAdm_view_supplier_panel(Panel x){


		x.setFont(new Font("Serif",Font.BOLD,16));
		x.setLayout(new GridBagLayout());
		
		Label adm_view_deptt_selectBy=new Label("  Sort by-");
		Label adm_view_deptt_orderBy=new Label("   Order by-");
		
		Choice c1=new Choice();
		Choice c2=new Choice();
		Choice c3=new Choice();
		c1.addItem("None");
		c1.addItem("Supplier ID");c1.addItem("Supplier Name");c1.addItem("Gender");c1.addItem("Contact");
		c1.addItem("Min Orders");c1.addItem("Max Orders");c1.addItem("Min Order Amount");c1.addItem("Max Order Amount");
		c2.add("None");
		c2.addItem("Supplier ID");c2.addItem("Supplier Name");c2.addItem("Gender");c2.addItem("Contact");
		
		c3.add("Ascending");
		c3.add("Descending");

		Button adm_view_deptt_ok=new Button ("  OK  ");
		TextField _adm_view_deptt_selectBy=new TextField(20);

		GridBagConstraints gbc=new GridBagConstraints();
		gbc.gridx=0;gbc.gridy=0;gbc.gridwidth=1;gbc.gridheight=1;gbc.anchor=GridBagConstraints.CENTER;
		gbc.weightx=10;gbc.weighty=1;


		gbc.gridx=0; x.add(adm_view_deptt_selectBy,gbc);
		gbc.gridx=1;x.add(c1,gbc);
		gbc.gridx=2; x.add(_adm_view_deptt_selectBy,gbc);
		gbc.gridx=3; x.add(adm_view_deptt_orderBy,gbc);
		gbc.gridx=4; x.add(c2,gbc);
		gbc.gridx=5; x.add(c3,gbc);
		gbc.gridx=8; x.add(adm_view_deptt_ok,gbc);

		gbc.gridx=0;
		gbc.gridy=1;gbc.gridwidth=10;
		//gbc.weightx=100;
		gbc.fill = GridBagConstraints.BOTH;
		JTable tbl=new JTable(1,1);
		JScrollPane sp=new JScrollPane(tbl);
		sp.setBackground(Color.RED);
		tbl.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		x.add(sp,gbc);
		
		adm_view_deptt_ok.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int sort=c1.getSelectedIndex();
				String value=_adm_view_deptt_selectBy.getText();
				int orderBy=c2.getSelectedIndex();
				int order=c3.getSelectedIndex();
				String query="select  SuppID,Name,Gender,ContactInfo from SUPPLIER where (SuppID<>'Unknown '";
				String orderBy_string=" ";
				String orderType=" ASC";
				String cond=" ) ";
				switch(orderBy){
					case 1:orderBy_string=" order by SuppID";break;
					case 2:orderBy_string=" order by Name";break;
					case 3:orderBy_string=" order by Gender";break;
					case 4:orderBy_string=" order by ContactInfo";break;
					default:orderBy_string=" ";
						
				}
				if(order==1){
					orderType=" DESC";
				}
				switch(sort){
					case 1:cond="&& SuppID='"+value+"')"		;break;
					case 2:cond="&& Name='"+value+"')"		;break;
					case 3:cond="&& Gender='"+value+"')"		;break;
					case 4:cond="&& ContactInfo='"+value+"')"		;break;
					default:cond=" )"		;
				}

				int noo=0;
				int toa=0;
				if(orderBy_string.equals(" "))orderType="";
				try{
					vector2=new Vector<Vector<String>>();
					int user=0;
					//System.out.print(query+cond+orderBy_string+orderType);
					rs=stmt.executeQuery(query+cond+orderBy_string+orderType);
					while(rs.next()){
						//System.out.println("select count(*) from _ORDER o , PRODUCT p where  o.ProdID=p.ProdID && p.SuppID='"+rs.getString(1)+"'");
						rs1=stmt1.executeQuery("select count(*) from _ORDER o , PRODUCT p where  o.ProdID=p.ProdID && p.SuppID='"+rs.getString(1)+"'");
						while(rs1.next())noo=Integer.parseInt(rs1.getString(1));
						
						rs1=stmt1.executeQuery("select sum(o.AmountTotal) from _ORDER  o , PRODUCT p where  o.ProdID=p.ProdID && p.SuppID='"+rs.getString(1)+"'");
						try{while(rs1.next())toa=Integer.parseInt(rs1.getString(1));}catch(Exception excep){toa=0;}
						
						vector=new Vector<String>();
						int given=0;
						if(sort>=5)given=Integer.parseInt(value);
						if((sort==5 && noo>=given)||(sort==6 && noo<=given) || (sort==7 && toa>=given) ||(sort==8 && toa<=given) || sort<=4){
						for(int i=1;i<5;i++)vector.add(rs.getString(i));
						vector.add(noo+"");vector.add(toa+"");
						vector2.add(vector);}
					}
										
					vector=new Vector<String>();
					vector.add("Supplier ID");vector.add("Supplier Name");vector.add("Gender");vector.add("Contact");
					vector.add("Orders");vector.add("Ordered Amount");

					//System.out.println(tbl.getModel().getClass());
					DefaultTableModel dtm=new DefaultTableModel(vector2,vector);
					tbl.setModel(dtm);
					dtm.fireTableDataChanged();
				}catch(Exception eee){eee.printStackTrace();}

				}
			});
		}
	
	public static void setupAdm_view_prod_panel(Panel x){
		x.setFont(new Font("Serif",Font.BOLD,16));
		x.setLayout(new GridBagLayout());
		
		Label adm_view_product_selectBy=new Label("  Sort by-");
		Label adm_view_product_orderBy=new Label("   Order by-");
		
		Choice c1=new Choice();
		Choice c2=new Choice();
		Choice c3=new Choice();
		c1.addItem("None");
		c1.addItem("Product ID");c1.addItem("Product Name");c1.addItem("Quantities");c1.addItem("Product Cost");
		c1.addItem("Supplier ID");c1.addItem("Product Brand");c1.addItem("Product Price");c1.addItem("Supplier Name");c1.add("Department");
		c2.add("None");
		c2.addItem("Product ID");c2.addItem("Product Name");c2.addItem("Quantities");c2.addItem("Product Cost");
		c2.addItem("Supplier ID");c2.addItem("Product Brand");c2.addItem("Product Price");c2.addItem("Supplier Name");c2.add("Department");

		c3.add("Ascending");
		c3.add("Descending");

		Button adm_view_product_ok=new Button ("  OK  ");
		TextField _adm_view_product_selectBy=new TextField(20);

		GridBagConstraints gbc=new GridBagConstraints();
		gbc.gridx=0;gbc.gridy=0;gbc.gridwidth=1;gbc.gridheight=1;gbc.anchor=GridBagConstraints.CENTER;
		gbc.weightx=10;gbc.weighty=1;


		gbc.gridx=0; x.add(adm_view_product_selectBy,gbc);
		gbc.gridx=1;x.add(c1,gbc);
		gbc.gridx=2; x.add(_adm_view_product_selectBy,gbc);
		gbc.gridx=3; x.add(adm_view_product_orderBy,gbc);
		gbc.gridx=4; x.add(c2,gbc);
		gbc.gridx=5; x.add(c3,gbc);
		gbc.gridx=8; x.add(adm_view_product_ok,gbc);

		gbc.gridx=0;
		gbc.gridy=1;gbc.gridwidth=10;
		//gbc.weightx=100;
		gbc.fill = GridBagConstraints.BOTH;
		JTable tbl=new JTable(1,1);
		JScrollPane sp=new JScrollPane(tbl);
		sp.setBackground(Color.RED);
		tbl.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		x.add(sp,gbc);
		
		adm_view_product_ok.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int sort=c1.getSelectedIndex();
				String value=_adm_view_product_selectBy.getText();
				int orderBy=c2.getSelectedIndex();
				int order=c3.getSelectedIndex();
				String query="select p.prodID,p.Name,p.QuantityInStock,p.Cost,p.SuppID,p.Price ,s.Name,p.Brand,d.Name from  SUPPLIER s, PRODUCT p,DEPARTMENT  d where( s.suppID=p.SuppID && p.ProdID<>'Unknown' && p.DepttId=d.DepttID   ";
				String orderBy_string=" ";
				String orderType=" ASC";
				String cond=" ) ";
				switch(orderBy){
					case 1:orderBy_string=" order by p.ProdID";break;
					case 2:orderBy_string=" order by p.Name";break;
					case 3:orderBy_string=" order by p.QuantityInStock";break;
					case 4:orderBy_string=" order by p.Cost";break;
					case 5:orderBy_string=" order by p.SuppID";break;
					case 6:orderBy_string=" order by p.Brand";break;
					case 7:orderBy_string=" order by P.Price";break;
					case 8:orderBy_string=" order by s.Name";break;
					case 9:orderBy_string=" order by d.Name";break;
					default:orderBy_string=" ";
						
				}
				if(order==1){
					orderType=" DESC";
				}
				switch(sort){
					case 1:cond=" && p.ProdID='"+_adm_view_product_selectBy.getText()+"')"		;break;
					case 2:cond=" && p.Name='"+_adm_view_product_selectBy.getText()+"')"		;break;
					case 3:cond=" && p.QuantityInStock='"+_adm_view_product_selectBy.getText()+"')"	;break;
					case 4:cond=" && p.Cost='"+_adm_view_product_selectBy.getText()+"')"		;break;
					case 5:cond=" && p.SuppID='"+_adm_view_product_selectBy.getText()+"')"		;break;
					case 6:cond=" && p.Brand='"+_adm_view_product_selectBy.getText()+"')"		;break;
					case 7:cond=" && p.Price='"+_adm_view_product_selectBy.getText()+"')"		;break;
					case 8:cond=" && s.Name='"+_adm_view_product_selectBy.getText()	+"')"	;break;
					case 9:cond=" && d.Name='"+_adm_view_product_selectBy.getText()	+"')"	;break;
					default:cond=" )"		;
				}
				if(orderBy_string.equals(" "))orderType="";
				try{
				vector2=new Vector<Vector<String>>();
				rs=stmt.executeQuery(query+cond+orderBy_string+orderType);
				while(rs.next()){
					vector=new Vector<String>();
					for(int i=1;i<10;i++)vector.add(rs.getString(i));
					vector2.add(vector);
					}
				vector=new Vector<String>();
				vector.add("ProductID");vector.add("Prod.Name");vector.add("QuantityInStock");vector.add("Cost");
				vector.add("SupplierID");vector.add("Price");vector.add("Supp. Name");vector.add("Brand");vector.add("Department");
				//System.out.println(tbl.getModel().getClass());
				DefaultTableModel dtm=new DefaultTableModel(vector2,vector);
				tbl.setModel(dtm);
				dtm.fireTableDataChanged();
				}catch(Exception eee){System.out.println(eee);}

				}
			});
	
		}
	
	public static void setupAdm_view_deptt_panel(Panel x){
		x.setFont(new Font("Serif",Font.BOLD,16));
		x.setLayout(new GridBagLayout());
		
		Label adm_view_deptt_selectBy=new Label("  Sort by-");
		Label adm_view_deptt_orderBy=new Label("   Order by-");
		
		Choice c1=new Choice();
		Choice c2=new Choice();
		Choice c3=new Choice();
		c1.addItem("None");
		c1.addItem("Department ID");c1.addItem("Department Name");c1.addItem("Manager ID");c1.addItem("Manager Name");
		c1.addItem("Min. no.of Products");c1.addItem("Max. no. of Products");c1.addItem("Min. no.of Employees");c1.addItem("Max. no. of Employees");
		c2.add("None");
		c2.addItem("Department ID");c2.addItem("Department Name");c2.addItem("Manager ID");c2.addItem("Manager Name");
		//c2.addItem("no. of Products");c2.addItem("no. of Employees");
		c3.add("Ascending");
		c3.add("Descending");

		Button adm_view_deptt_ok=new Button ("  OK  ");
		TextField _adm_view_deptt_selectBy=new TextField(20);

		GridBagConstraints gbc=new GridBagConstraints();
		gbc.gridx=0;gbc.gridy=0;gbc.gridwidth=1;gbc.gridheight=1;gbc.anchor=GridBagConstraints.CENTER;
		gbc.weightx=10;gbc.weighty=1;


		gbc.gridx=0; x.add(adm_view_deptt_selectBy,gbc);
		gbc.gridx=1;x.add(c1,gbc);
		gbc.gridx=2; x.add(_adm_view_deptt_selectBy,gbc);
		gbc.gridx=3; x.add(adm_view_deptt_orderBy,gbc);
		gbc.gridx=4; x.add(c2,gbc);
		gbc.gridx=5; x.add(c3,gbc);
		gbc.gridx=8; x.add(adm_view_deptt_ok,gbc);

		gbc.gridx=0;
		gbc.gridy=1;gbc.gridwidth=10;
		//gbc.weightx=100;
		gbc.fill = GridBagConstraints.BOTH;
		JTable tbl=new JTable(1,1);
		JScrollPane sp=new JScrollPane(tbl);
		sp.setBackground(Color.RED);
		tbl.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		x.add(sp,gbc);
		
		adm_view_deptt_ok.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int sort=c1.getSelectedIndex();
				String value=_adm_view_deptt_selectBy.getText();
				int orderBy=c2.getSelectedIndex();
				int order=c3.getSelectedIndex();
				String query="select d.DepttID,d.Name ,d.ManagerID ,e.Name from DEPARTMENT d ,EMPLOYEE e where (d.DepttID<>'Unknown' && d.ManagerID=e.EmpID ";
				String orderBy_string=" ";
				String orderType=" ASC";
				String cond=" ) ";
				switch(orderBy){
					case 1:orderBy_string=" order by d.DepttID";break;
					case 2:orderBy_string=" order by d.Name";break;
					case 3:orderBy_string=" order by d.ManagerID";break;
					case 4:orderBy_string=" order by e.Name";break;
					default:orderBy_string=" ";
						
				}
				if(order==1){
					orderType=" DESC";
				}
				switch(sort){
					case 1:cond=" && d.DepttID='"+_adm_view_deptt_selectBy.getText()+"')"		;break;
					case 2:cond=" && d.Name='"+_adm_view_deptt_selectBy.getText()+"')"		;break;
					case 3:cond=" && d.ManagerID='"+_adm_view_deptt_selectBy.getText()+"')"	;break;
					case 4:cond=" && e.Name='"+_adm_view_deptt_selectBy.getText()+"')"		;break;
					default:cond=" )"		;
				}
				int nop=0;
				int noe=0;
				if(orderBy_string.equals(" "))orderType="";
				try{
				vector2=new Vector<Vector<String>>();
				
				rs=stmt.executeQuery(query+cond+orderBy_string+orderType);
				while(rs.next()){
					rs1=stmt1.executeQuery("select count(*) from EMPLOYEE where DepttID='"+rs.getString(1)+"'");
					while(rs1.next())noe=Integer.parseInt(rs1.getString(1));
					rs1=stmt1.executeQuery("select count(*) from PRODUCT where DepttID='"+rs.getString(1)+"'");
					while(rs1.next())nop=Integer.parseInt(rs1.getString(1));
					vector=new Vector<String>();
					int given=0;
					if(sort>=5)given=Integer.parseInt(value);
					if((sort==5 && nop>=given)||(sort==6 && nop<=given) || (sort==7 && noe>=given) ||(sort==8 && noe<=given) || sort<=4){
						for(int i=1;i<5;i++)vector.add(rs.getString(i));
						vector.add(nop+"");vector.add(noe+"");
						vector2.add(vector);}
					}
				vector=new Vector<String>();
				vector.add("Department ID");vector.add("Department Name");vector.add("ManagerID");vector.add("Manager Name");
				vector.add("Total Products");vector.add("Total Employees");
				//System.out.println(tbl.getModel().getClass());
				DefaultTableModel dtm=new DefaultTableModel(vector2,vector);
				tbl.setModel(dtm);
				dtm.fireTableDataChanged();
				}catch(Exception eee){eee.printStackTrace();}

				}
			});
		}
	
	public static void setupAdm_view_sales_panel(Panel x){
		x.setFont(new Font("Serif",Font.BOLD,16));
		x.setLayout(new GridBagLayout());
		
		Label emp_view_order_selectBy=new Label("  Sort by-");
		Label emp_view_order_orderBy=new Label("   Order by-");
	
	
		Choice c1=new Choice();
		Choice c2=new Choice();
		Choice c3=new Choice();
		c1.addItem("None");
		c1.addItem("Sell no");c1.addItem("Departmen ID");c1.addItem("Product ID");c1.addItem("Product Name");c1.addItem("Quantities");
		c1.addItem("Product Price");c1.addItem("Product Brand");c1.addItem("Sell Date");c1.addItem("Total Price");

		c2.addItem("Sell no");c2.addItem("Department ID");c2.addItem("Product ID");c2.addItem("Product Name");c2.addItem("Quantities");
		c2.addItem("Product Price");c2.addItem("Product Brand");c2.addItem("Sell Date");c2.addItem("Total Price");

		c3.add("Ascending");
		c3.add("Descending");

		Button emp_view_order_ok=new Button ("  OK  ");
		TextField _emp_view_order_selectBy=new TextField(20);

		GridBagConstraints gbc=new GridBagConstraints();
		gbc.gridx=0;gbc.gridy=0;gbc.gridwidth=1;gbc.gridheight=1;gbc.anchor=GridBagConstraints.CENTER;
		gbc.weightx=10;gbc.weighty=1;


		gbc.gridx=0; x.add(emp_view_order_selectBy,gbc);
		gbc.gridx=1;x.add(c1,gbc);
		gbc.gridx=2; x.add(_emp_view_order_selectBy,gbc);
		gbc.gridx=3; x.add(emp_view_order_orderBy,gbc);
		gbc.gridx=4; x.add(c2,gbc);
		gbc.gridx=5; x.add(c3,gbc);
		gbc.gridx=8; x.add(emp_view_order_ok,gbc);
		
		gbc.gridx=0;
		gbc.gridy=1;gbc.gridwidth=10;
		//gbc.weightx=100;
		gbc.fill = GridBagConstraints.BOTH;
		JTable tbl=new JTable(1,1);
		JScrollPane sp=new JScrollPane(tbl);
		sp.setBackground(Color.RED);
		tbl.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		x.add(sp,gbc);


		emp_view_order_ok.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int sort=c1.getSelectedIndex();
				String value=_emp_view_order_selectBy.getText();
				int orderBy=c2.getSelectedIndex();
				int order=c3.getSelectedIndex();
				String query="select  o.SaleNo,p.DepttID ,o.Date_Time,o.prodID,p.Name,p.Brand,o.Price,o.Units,o.AmountTotal from SALES_RECORD o, PRODUCT p where( o.ProdID=p.ProdID  ";
				String orderBy_string=" order by o.SaleNo";
				String orderType=" ASC";
				String cond=" ) ";
				switch(orderBy){
					case 0:orderBy_string=" order by o.SaleNo";break;
					case 2:orderBy_string=" order by o.ProdID";break;
					case 1:orderBy_string=" order by p.DepttID";break;
					case 3:orderBy_string=" order by p.Name";break;
					case 4:orderBy_string=" order by o.Units";break;
					case 5:orderBy_string=" order by o.Price";break;
					case 6:orderBy_string=" order by p.Brand";break;
					case 7:orderBy_string=" order by o.Date_Time";break;
					case 8:orderBy_string=" order by o.AmountTotal";break;
					default:orderBy_string=" order by o.SaleNo";
						
				}
				if(order==1){
					orderType=" DESC";
				}
				switch(sort){
					case 1:cond="&& o.SaleNo='"+_emp_view_order_selectBy.getText()+"')"		;break;
					case 3:cond=" && o.ProdID='"+_emp_view_order_selectBy.getText()+"')"		;break;
					case 2:cond=" && p.DepttID='"+_emp_view_order_selectBy.getText()+"')"		;break;
					case 4:cond=" && p.Name='"+_emp_view_order_selectBy.getText()+"')"		;break;
					case 5:cond=" && o.Units='"+_emp_view_order_selectBy.getText()+"')"	;break;
					case 6:cond=" && o.Price='"+_emp_view_order_selectBy.getText()+"')"		;break;
					case 7:cond=" && p.Brand='"+_emp_view_order_selectBy.getText()+"')"		;break;
					case 8:cond=" && o.Date_Time='"+_emp_view_order_selectBy.getText()+"')"		;break;
					case 9:cond=" && o.AmountTotal='"+_emp_view_order_selectBy.getText()	+"')"	;break;
					default:cond=" )"		;
				}
				try{
				vector2=new Vector<Vector<String>>();
				int count=0;
				rs=stmt.executeQuery(query+cond+orderBy_string+orderType);
				while(rs.next()){
				//	String newTemp="";
					count++;
					vector=new Vector<String>();
					for(int i=1;i<10;i++)vector.add(rs.getString(i));
					vector2.add(vector);
					}
				vector=new Vector<String>();
				vector.add("Sell No.");vector.add("DepartmentID");vector.add("Date");vector.add("ProductID");vector.add("Prod.Name");vector.add("Brand");
				vector.add("Price");vector.add("Units");vector.add("TotalPrice");
				//System.out.println(tbl.getModel().getClass());
				DefaultTableModel dtm=new DefaultTableModel(vector2,vector);
				tbl.setModel(dtm);
				dtm.fireTableDataChanged();
				
				}catch(Exception eee){eee.printStackTrace();}

				}
			});
		}
	
	//public static void setupAdm_view_revenue_panel(Panel x){x.add(new Label("Admin view revenue"));}
	
	public static void setupAdm_view_order_panel(Panel x){
		x.setFont(new Font("Serif",Font.BOLD,16));
		x.setLayout(new GridBagLayout());
		
		Label emp_view_order_selectBy=new Label("  Sort by-");
		Label emp_view_order_orderBy=new Label("   Order by-");
	
	
		Choice c1=new Choice();
		Choice c2=new Choice();
		Choice c3=new Choice();
		c1.addItem("None");
		c1.addItem("Order no");c1.addItem("Departmen ID");c1.addItem("Product ID");c1.addItem("Product Name");c1.addItem("Quantities");c1.addItem("Product Cost");
		c1.addItem("Supplier ID");c1.addItem("Product Brand");c1.addItem("Order Date");c1.addItem("Total Cost");

		c2.addItem("Order no");c2.addItem("Department ID");c2.addItem("Product ID");c2.addItem("Product Name");c2.addItem("Quantities");c2.addItem("Product Cost");
		c2.addItem("Supplier ID");c2.addItem("Product Brand");c2.addItem("Order Date");c2.addItem("Total Cost");

		c3.add("Ascending");
		c3.add("Descending");

		Button emp_view_order_ok=new Button ("  OK  ");
		TextField _emp_view_order_selectBy=new TextField(20);

		GridBagConstraints gbc=new GridBagConstraints();
		gbc.gridx=0;gbc.gridy=0;gbc.gridwidth=1;gbc.gridheight=1;gbc.anchor=GridBagConstraints.CENTER;
		gbc.weightx=10;gbc.weighty=1;


		gbc.gridx=0; x.add(emp_view_order_selectBy,gbc);
		gbc.gridx=1;x.add(c1,gbc);
		gbc.gridx=2; x.add(_emp_view_order_selectBy,gbc);
		gbc.gridx=3; x.add(emp_view_order_orderBy,gbc);
		gbc.gridx=4; x.add(c2,gbc);
		gbc.gridx=5; x.add(c3,gbc);
		gbc.gridx=8; x.add(emp_view_order_ok,gbc);
		
		gbc.gridx=0;
		gbc.gridy=1;gbc.gridwidth=10;
		//gbc.weightx=100;
		gbc.fill = GridBagConstraints.BOTH;
		JTable tbl=new JTable(1,1);
		JScrollPane sp=new JScrollPane(tbl);
		sp.setBackground(Color.RED);
		tbl.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		x.add(sp,gbc);


		emp_view_order_ok.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int sort=c1.getSelectedIndex();
				String value=_emp_view_order_selectBy.getText();
				int orderBy=c2.getSelectedIndex();
				int order=c3.getSelectedIndex();
				String query="select  o.OrderID,p.DepttID ,o.OrderDate,o.prodID,p.Name,p.Brand,p.SuppID,o.Cost,o.Quantity,o.AmountTotal from _ORDER o, PRODUCT p where( o.ProdID=p.ProdID  ";
				String orderBy_string=" order by o.OrderID";
				String orderType=" ASC";
				String cond=" ) ";
				switch(orderBy){
					case 0:orderBy_string=" order by o.OrderID";break;
					case 2:orderBy_string=" order by o.ProdID";break;
					case 1:orderBy_string=" order by p.DepttID";break;
					case 3:orderBy_string=" order by p.Name";break;
					case 4:orderBy_string=" order by o.Quantity";break;
					case 5:orderBy_string=" order by o.Cost";break;
					case 6:orderBy_string=" order by p.SuppID";break;
					case 7:orderBy_string=" order by p.Brand";break;
					case 8:orderBy_string=" order by o.OrderDateSaleNo";break;
					case 9:orderBy_string=" order by o.AmountTotal";break;
					default:orderBy_string=" order by o.OrderID";
						
				}
				if(order==1){
					orderType=" DESC";
				}
				switch(sort){
					case 1:cond="&& o.OrderID='"+_emp_view_order_selectBy.getText()+"')"		;break;
					case 3:cond=" && o.ProdID='"+_emp_view_order_selectBy.getText()+"')"		;break;
					case 2:cond=" && p.DepttID='"+_emp_view_order_selectBy.getText()+"')"		;break;
					case 4:cond=" && p.Name='"+_emp_view_order_selectBy.getText()+"')"		;break;
					case 5:cond=" && o.Quantity='"+_emp_view_order_selectBy.getText()+"')"	;break;
					case 6:cond=" && o.Cost='"+_emp_view_order_selectBy.getText()+"')"		;break;
					case 7:cond=" && p.SuppID='"+_emp_view_order_selectBy.getText()+"')"		;break;
					case 8:cond=" && p.Brand='"+_emp_view_order_selectBy.getText()+"')"		;break;
					case 9:cond=" && o.OrderDate='"+_emp_view_order_selectBy.getText()+"')"		;break;
					case 10:cond=" && o.AmountTotal='"+_emp_view_order_selectBy.getText()	+"')"	;break;
					default:cond=" )"		;
				}
				try{
				vector2=new Vector<Vector<String>>();
				int count=0;
				rs=stmt.executeQuery(query+cond+orderBy_string+orderType);
				while(rs.next()){
				//	String newTemp="";
					count++;
					vector=new Vector<String>();
					for(int i=1;i<11;i++)vector.add(rs.getString(i));
					vector2.add(vector);
					}
				vector=new Vector<String>();
				vector.add("Order No.");vector.add("DepartmentID");vector.add("Date");vector.add("ProductID");vector.add("Prod.Name");vector.add("Brand");
				vector.add("SuppID");vector.add("Cost");vector.add("Quantity");vector.add("TotalCost");
				//System.out.println(tbl.getModel().getClass());
				DefaultTableModel dtm=new DefaultTableModel(vector2,vector);
				tbl.setModel(dtm);
				dtm.fireTableDataChanged();
				
				}catch(Exception eee){eee.printStackTrace();}

				}
			});
		}




	}	