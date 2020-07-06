package mall.order;
import java.sql.*;
import java.util.*;
import mall.util.*;
/**
 * �ֹ��� ��ǰ�鿡 ���� �ڹٺ� 
 */	
public class ItemBean {
  	private int itemId = 0;			//�����۹�ȣ
  	private int productId = 0;  	//��ǰ��ȣ
  	private int orderId = 0;  		//�ֹ���ȣ
	private int qty = 0;			//����
	
	public int getItemId(){ return this.itemId;}
	public void setItemId(int itemId) { this.itemId = itemId; }
	public int getProductId(){ return this.productId;}
	public void setProductId(int productId) { this.productId = productId; }	
	public int getOrderId(){ return this.orderId;}
	public void setOrderId(int orderId) { this.orderId = orderId; }	
	public int getQty(){ return this.qty;}
	public void setQty(int qty) { this.qty = qty; }
	
    /** ������ */
	public ItemBean() {}		
  	
	/** Item��ȣ�� ��ȸ
	 * @param Item��ȣ int
	 * @return ��ȸ��� ����
	 */
	public void init(int item_id) throws Exception {
	    Connection con = null;
  		PreparedStatement ps = null;
  		ResultSet rs = null;
  		StringBuffer qry = null;		 	
	    try {     				
			//�����ͺ��̽� ����
			con = DBManager.getConnection(); 

			//SQL�� �ۼ�
			qry = new StringBuffer(1024);		    
			qry.append("SELECT * FROM items WHERE item_id = ? ");        	    	
		
	    	ps  = con.prepareStatement(qry.toString());
			ps.setInt(1, item_id);

			//SQL�� ����
	      	rs = ps.executeQuery();	   
			
			//SQL�� �������� ItemBean ��ü�� ����
	      	while (rs.next()) {
	        	setValues(rs);				    
	     	}
	     	rs.close();
	     	ps.close();	      	 
	    }
	    catch(Exception e) {	      	
			throw e;	  	
	    }
		finally	{			
			try { if (rs!=null) rs.close(); } catch(SQLException sqle) {}
			try { if (ps!=null) ps.close(); } catch(SQLException sqle) {}
			try { if (con!=null) con.close(); } catch(SQLException sqle) {}
		}
    }
  
    /** �ű� Item���� �߰�
	 * @return �ű��߰���� ����
	 */
    public void add() throws Exception {
	    Connection con = null;
  		PreparedStatement ps = null;
  		ResultSet rs = null;
  		StringBuffer qry = null;    	
		try {
		   	//�����ͺ��̽� ����
			con = DBManager.getConnection();		
			
			//SQL�� �ۼ�
		   	qry = new StringBuffer(1024);			          
	      	qry.append("INSERT INTO items (item_id, product_id, order_id, qty) ");			
			qry.append("VALUES(item_id_seq.nextval, ?,?,?)");
			
			ps = con.prepareStatement(qry.toString());	
	      	setPS(ps, "add");       
	      	
			//SQL�� ����
	      	ps.executeUpdate();
	      	ps.close();				
	      
			//�����۹�ȣ�� ������� SQL�� �ۼ�
			ps = con.prepareStatement("select max(item_id) itemId from items ");            
            rs = ps.executeQuery();				
			
			//�����۹�ȣ ���
			while (rs.next()) {				
				itemId = rs.getInt("itemId");	
			}
			     	
	      	rs.close();
	      	ps.close();	      	
    	} 
		catch (Exception e) {      		  		
			throw e;
    	}
		finally	{			
			try { if (rs!=null) rs.close(); } catch(SQLException sqle) {}
			try { if (ps!=null) ps.close(); } catch(SQLException sqle) {}
			try { if (con!=null) con.close(); } catch(SQLException sqle) {}
		}
  	}
	
    /** �ش� Item��ȣ�� Item���� ����
	 * @return ������� ����
	 */
    public void modify() throws Exception {	    
		Connection con = null;
  		PreparedStatement ps = null;
  		StringBuffer qry = null;	
		try {	      	
			//�����ͺ��̽� ����
			con = DBManager.getConnection();	

			//SQL�� �ۼ�
			qry = new StringBuffer(1024);
	      	qry.append("UPDATE items SET product_id = ?, order_id = ?, qty = ? ");
            qry.append("WHERE item_id = ?");
	
	      	ps = con.prepareStatement(qry.toString());			
	      	setPS(ps, "modify");		
			
	      	//SQL�� ����
			ps.executeUpdate();	
	      	ps.close();		  	   
	    } 
		catch (Exception e) {	      	
			throw e;
	    }
		finally	{			
			try { if (ps!=null) ps.close(); } catch(SQLException sqle) {}
			try { if (con!=null) con.close(); } catch(SQLException sqle) {}
		}		
	} 
	
	/** �ش� Item��ȣ�� Item���� ����
	 * @return ������� ����
	 */
	public void remove() throws Exception {	    
		Connection con = null;
  		PreparedStatement ps = null;
  		ResultSet rs = null;
  		StringBuffer qry = null;		
		try {
	      	//�����ͺ��̽� ����
			con = DBManager.getConnection();

			//SQL�� �ۼ�
			qry = new StringBuffer(1024);
	      	qry.append("DELETE FROM items WHERE item_id = ?");	
	      	ps = con.prepareStatement(qry.toString());			
	      	ps.setInt(1, itemId);      
						
	      	//SQL�� ����
			ps.executeUpdate();	
	      	ps.close();		  	   
	    } 
		catch (Exception e) {	      	
			throw e;
	    }
		finally	{			
			try { if (rs!=null) rs.close(); } catch(SQLException sqle) {}
			try { if (ps!=null) ps.close(); } catch(SQLException sqle) {}
			try { if (con!=null) con.close(); } catch(SQLException sqle) {}
		}			
	} 	
		
	/** ��� �ֹ��� ��ǰ����Ʈ �˻�
	 * @return ItemBean �迭
	 */
	public ItemBean[] findAll() throws Exception {                				
		Connection con = null;
  		PreparedStatement ps = null;
  		ResultSet rs = null;
  		StringBuffer qry = null;
		ItemBean[] items = null;
		try {					
			//�����ͺ��̽� ����
			con = DBManager.getConnection();		

			//SQL�� �ۼ�
			qry = new StringBuffer(1024);		
			qry.append("select * from items ");
			
            ps = con.prepareStatement(qry.toString());           	
			//SQL�� ����
			rs = ps.executeQuery();

			//SQL�� �������� ItemBean ��ü �迭�� ����
			items = setValuesForFind(rs);						
			ps.close();
			rs.close();			
			
        }
      	catch(Exception e) {      
			throw e;
		}
		finally {			
			try { if (rs!=null) rs.close(); } catch(SQLException sqle) {}
			try { if (ps!=null) ps.close(); } catch(SQLException sqle) {}
			try { if (con!=null) con.close(); } catch(SQLException sqle) {}
		}
		return items;
    }
	
	/** �ֹ���ȣ�� ��ǰ����Ʈ �˻�
	 * @param �ֹ���ȣ int
	 * @return ItemBean �迭
	 */
	public ItemBean[] findByOrderId(int order_id) throws Exception {                				
		Connection con = null;
  		PreparedStatement ps = null;
  		ResultSet rs = null;
  		StringBuffer qry = null;
		ItemBean[] items = null;
		try {						
			//�����ͺ��̽� ����
			con = DBManager.getConnection();		

			//SQL�� �ۼ�
			qry = new StringBuffer(1024);		
			qry.append("select * from items where order_id = ?");
			
            ps = con.prepareStatement(qry.toString());    
            ps.setInt(1, order_id);

			//SQL�� ����
			rs = ps.executeQuery();

			//SQL�� �������� ItemBean ��ü �迭�� ����
			items = setValuesForFind(rs);			
			
			ps.close();
			rs.close();				
        }
      	catch(Exception e) {      
			throw e;
		}
		finally	{			
			try { if (rs!=null) rs.close(); } catch(SQLException sqle) {}
			try { if (ps!=null) ps.close(); } catch(SQLException sqle) {}
			try { if (con!=null) con.close(); } catch(SQLException sqle) {}
		}
		return items;
    }
	
	/** ResultSet ���� ItemBean �迭�� ����
	 * @param ResultSet
	 * @return ItemBean �迭
	 */
	private ItemBean[] setValuesForFind(ResultSet rs) throws Exception {
		ItemBean[] items = null; 
		ItemBean item = null;
		LinkedList lkl = null;			

		//ResultSet ���� LinkedList�� ����
		lkl = new LinkedList();		        		
        for(; rs.next(); lkl.add(item)) {                
	        item = new ItemBean();			
			item.setValues(rs);		
        }  
		//LinkedList�� ItemBean �迭�� ��ȯ
        items = new ItemBean[lkl.size()];
        items = (ItemBean[])lkl.toArray(items);			
		return items;
	}
	
	/** ResultSet ��ü�� ���� ��������� ����
	 * @param ResultSet
	 */
	private void setValues(ResultSet rs) throws Exception {      	        
        itemId = rs.getInt("item_id");
        productId = rs.getInt("product_id");        
        orderId = rs.getInt("order_id");              
		qty = rs.getInt("qty");   				
    }
	
	/** ������� ���� PreparedStatement ��ü�� ����
	 * @param PreparedStatement
	 * @param Ÿ��(insert/modify)
	 */
	private void setPS(PreparedStatement ps, String type) throws Exception {
		ps.clearParameters();	   	
	   	ps.setInt(1, productId);
	   	ps.setInt(2, orderId);	   	
	   	ps.setInt(3, qty);
	   	if (type.equals("modify")) {
			ps.setInt(4, itemId);             
		}
    }
}