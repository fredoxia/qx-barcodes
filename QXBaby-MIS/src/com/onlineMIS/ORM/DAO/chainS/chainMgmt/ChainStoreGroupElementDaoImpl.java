package com.onlineMIS.ORM.DAO.chainS.chainMgmt;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainStoreGroup;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainStoreGroupElement;
import com.onlineMIS.ORM.entity.headQ.finance.FinanceCategory;


@Repository
public class ChainStoreGroupElementDaoImpl extends BaseDAO<ChainStoreGroupElement> {

	/**
	 * 删除chain group element by groupId
	 * @param chainGroupId
	 */
	public int deleteGroupEleByGroupId(int chainGroupId) {
		String sql = "DELETE FROM ChainStoreGroupElement C WHERE C.chainGroup.id = ?";
		Object[] values = new Object[]{chainGroupId};
		
		int success = this.executeHQLUpdateDelete(sql, values, true);
		return success;
	}
	
	/**
	 * 删除chain group element by groupId
	 * @param chainGroupId
	 */
	public int deleteEleByChainId(int chainId) {
		String sql = "DELETE FROM ChainStoreGroupElement C WHERE C.chainId = ?";
		Object[] values = new Object[]{chainId};
		
		int success = this.executeHQLUpdateDelete(sql, values, true);
		return success;
	}

	/**
	 * 检查chain是否存在于非这个chainGroupId的其他chainGroup里面
	 * @param chainId
	 * @param chainGroupId
	 * @return
	 */
	public int checkExistOfChain(Integer chainId, int chainGroupId) {
		String sql = "SELECT COUNT(*) FROM ChainStoreGroupElement C WHERE C.chainId = ? AND C.chainGroup.id <> ?";
		Object[] values = new Object[]{chainId,chainGroupId};
		
		int count = this.executeHQLCount(sql, values, true);
		
		return count;
	}
	

}
