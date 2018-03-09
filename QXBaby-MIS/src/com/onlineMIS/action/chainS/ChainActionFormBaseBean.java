package com.onlineMIS.action.chainS;


public class ChainActionFormBaseBean{
	  
    /**
     * the parms to control the button
     */
    private boolean canSaveDraft = false;
    private boolean canPost = false;
    private boolean canDelete = false;
    private boolean canCancel = false;
    private boolean canEdit = false;
    private boolean canCopy = false;
    private boolean canEditOrderDate = false;
    private boolean canConfirm = false;
    private boolean canReject = false;

    
	public boolean isCanConfirm() {
		return canConfirm;
	}
	public void setCanConfirm(boolean canConfirm) {
		this.canConfirm = canConfirm;
	}
	public boolean isCanReject() {
		return canReject;
	}
	public void setCanReject(boolean canReject) {
		this.canReject = canReject;
	}
	public boolean isCanSaveDraft() {
		return canSaveDraft;
	}
	public void setCanSaveDraft(boolean canSaveDraft) {
		this.canSaveDraft = canSaveDraft;
	}
	public boolean isCanPost() {
		return canPost;
	}
	public void setCanPost(boolean canPost) {
		this.canPost = canPost;
	}
	public boolean isCanDelete() {
		return canDelete;
	}
	public void setCanDelete(boolean canDelete) {
		this.canDelete = canDelete;
	}
	public boolean isCanCancel() {
		return canCancel;
	}
	public void setCanCancel(boolean canCancel) {
		this.canCancel = canCancel;
	}
	public boolean isCanEdit() {
		return canEdit;
	}
	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}
	public boolean isCanCopy() {
		return canCopy;
	}
	public void setCanCopy(boolean canCopy) {
		this.canCopy = canCopy;
	}
	public boolean isCanEditOrderDate() {
		return canEditOrderDate;
	}
	public void setCanEditOrderDate(boolean canEditOrderDate) {
		this.canEditOrderDate = canEditOrderDate;
	}
	
}
