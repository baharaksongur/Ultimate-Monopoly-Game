package game;

public class Stock {

	private String name;
	private int currentNumberOfShare;
	private int priceOfShare;
	private int loanPrice;
	private int dividentPrice1;
	private int dividentPrice2;
	private int dividentPrice3;
	private int dividentPrice4;
	private int dividentPrice5;
	private Object[] auction;
	

	private int[] dividends;

	public Stock(String name, int currentNumberOfShare,int priceOfShare, int loanPrice ,
			int dividentPrice1,int dividentPrice2, int dividentPrice3, int dividentPrice4, int dividentPrice5){
		this.name=name;
		this.currentNumberOfShare=currentNumberOfShare;
		this.priceOfShare=priceOfShare;
		this.loanPrice=loanPrice;
		this.dividentPrice1=dividentPrice1;
		this.dividentPrice2=dividentPrice2;
		this.dividentPrice3=dividentPrice3;
		this.dividentPrice4=dividentPrice4;
		this.dividentPrice5=dividentPrice5;
		auction=new Object[]{false,0};
		
		dividends=new int[]{dividentPrice1,dividentPrice2,dividentPrice3, dividentPrice4, dividentPrice5};

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCurrentNumberOfShare() {
		return currentNumberOfShare;
	}

	public void setCurrentNumberOfShare(int currentNumberOfShare) {
		this.currentNumberOfShare = currentNumberOfShare;
	}

	public int getPriceOfShare() {
		return priceOfShare;
	}
	
	public Object[] getAuction() {
		return auction;
	}

	public void setAuction(Object[] auction) {
		this.auction = auction;
	}

	public void setPriceOfShare(int priceOfShare) {
		this.priceOfShare = priceOfShare;
	}

	public int getLoanPrice() {
		return loanPrice;
	}
	
	public int[] getDividends() {
		return dividends;
	}

	public void setLoanPrice(int loanPrice) {
		this.loanPrice = loanPrice;
	}

	public int getDividentPrice1() {
		return dividentPrice1;
	}

	public void setDividentPrice1(int dividentPrice1) {
		this.dividentPrice1 = dividentPrice1;
	}

	public int getDividentPrice2() {
		return dividentPrice2;
	}

	public void setDividentPrice2(int dividentPrice2) {
		this.dividentPrice2 = dividentPrice2;
	}

	public int getDividentPrice3() {
		return dividentPrice3;
	}

	public void setDividentPrice3(int dividentPrice3) {
		this.dividentPrice3 = dividentPrice3;
	}

	public int getDividentPrice4() {
		return dividentPrice4;
	}

	public void setDividentPrice4(int dividentPrice4) {
		this.dividentPrice4 = dividentPrice4;
	}

	public int getDividentPrice5() {
		return dividentPrice5;
	}

	public void setDividentPrice5(int dividentPrice5) {
		this.dividentPrice5 = dividentPrice5;
	}
}
