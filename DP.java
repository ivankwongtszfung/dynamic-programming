
public class DP {	
	public static int counter=0;
	public static String result="";
	public static void main(String[] args){
		/*local environment input method
		@SuppressWarnings("resource")
		Scanner input=new Scanner(System.in);
		String r=input.nextLine();
		String s=input.nextLine();
		int match=input.nextInt();
		int mismatch=input.nextInt();
		int penalty=input.nextInt();
		int m=r.length()+1;
		int n=s.length()+1;
		int[][] a=new int[m][n];
		char leftc,rightc;
		a[m-1][n-1]=0;
		*/
		String r=args[0];
		String s=args[1];
		int match=Integer.parseInt(args[2]);
		int mismatch=Integer.parseInt(args[3]);
		int penalty=Integer.parseInt(args[4]);
		int m=r.length()+1;
		int n=s.length()+1;	
		int[][] a=new int[m][n];
		char leftc,rightc;
		a[m-1][n-1]=0;
		
		int row=m-1;
		int column=n-1;
		int check;
		while(row>=0){
			while(column>=0){
				//1. need to avoid out loop case
				//case 1: index.n,vertical only
				//case 2: index.m,horizontal only
				//case 3: index m,n no function
				
				if(row==m-1 && column==n-1){
					column--;
					
				}
				else if(row==m-1){
					a[row][column]=horizontal(a,row,column,penalty);
					column--;
					
				}
				else if(column==n-1){
					a[row][column]=vertical(a,row,column,penalty);
					column--;
					
				}
				else{
					leftc=r.charAt(row);
					rightc=s.charAt(column);
					check=horizontal(a,row,column,penalty);
					if(check<vertical(a,row,column,penalty))
						check=vertical(a,row,column,penalty);
					if(check<hypotenuse(a,row,column,leftc,rightc,match,mismatch))
						check=hypotenuse(a,row,column,leftc,rightc,match,mismatch);
					a[row][column]=check;
					
					column--;
					
				}
				
			}
			column=n-1;
			row--;
		}
		row=0;column=0;
		//list array
		
		 while(row<m){
			while(column<n){
				System.out.print('|');
				if(a[row][column]<0)
					System.out.print(a[row][column]);
				else
					System.out.print(" "+a[row][column]);
				column++;
			}
			column=0;
			System.out.println('|');
			
			row++;
		}
		
		String Rresult=new String("");
		String Sresult=new String("");
		String result =new String("");
		backtrace(0,0,a,r,s,m,n,match,mismatch,penalty,result,Rresult,Sresult,0);
		
		return;
	}
	
	
	public static int backtrace(int row,int column,int[][] array,String r,String s,int m,int n,int match,int mismatch,int penalty,String result,String Rresult,String Sresult,int counter){
		//check the value of horizontal
			//horizontal check
			
			if(column<n-1 && row<m-1){
			if(((array[row][column]-array[row][column+1])==penalty)){
				Rresult+='_';
				Sresult+=s.charAt(column);
				column+=1;
				
				backtrace(row,column,array,r,s,m,n,match,mismatch,penalty,result,Rresult,Sresult,counter);
				
				column--;
				Sresult=Sresult.substring(0,Sresult.length()-1);
				Rresult=Rresult.substring(0,Rresult.length()-1);
				
			}
			//vertical check
			if(((array[row][column]-array[row+1][column])==penalty) ){
				Rresult+=r.charAt(row);
				Sresult+='_';
				row+=1;
				
				backtrace(row,column,array,r,s,m,n,match,mismatch,penalty,result,Rresult,Sresult,counter);
				
				row--;
				Sresult=Sresult.substring(0,Sresult.length()-1);
				Rresult=Rresult.substring(0,Rresult.length()-1);
				
			}
			//hypotenuse check
			//if match
			//==match
			//if not match
			//==mismatch
			//|| (array[row][column]-array[row+1][column+1])==mismatch
			if(((array[row][column]-array[row+1][column+1])==match && r.charAt(row)==s.charAt(column))||((array[row][column]-array[row+1][column+1])==mismatch && r.charAt(row)!=s.charAt(column))) {  
				
				Rresult+=r.charAt(row);
				Sresult+=s.charAt(column);
				row+=1;
				column+=1;
				
				backtrace(row,column,array,r,s,m,n,match,mismatch,penalty,result,Rresult,Sresult,counter);
				
				row--;
				column--;
				Sresult=Sresult.substring(0,Sresult.length()-1);
				Rresult=Rresult.substring(0,Rresult.length()-1);
				
				
			}
			}
			//m=row:3+1,n=column:3+1
			//m-1=3,n-1=3
			if(row==m-1 && column==n-1){
				DP.counter++;
				result=Rresult+"\n"+Sresult+"\n";
				DP.result+="\n"+result;
				row++;column++;
			}
			if(row==0 && column==0){
				
				System.out.println(DP.counter);
				System.out.print(DP.result);
				return counter;
			}
			else{
				return 0;
			}
		
			
			
	}

	
	public static int horizontal(int[][] array,int i,int j,int pen){
		int x;
		x=-1000000;
		x=array[i][j+1];
		x+=pen;
		return x;
	}
	
	public static int vertical(int[][] array,int i,int j,int pen){
		int x;
		x=-1000000;
		x=array[i+1][j];
		x+=pen;
		
		return x;
	}
	
	public static int hypotenuse(int[][] array,int i,int j,char s,char r,int match,int mismatch){
		int x;
		x=-1000000;
		if(s==r){
			x=array[i+1][j+1];
			x+=match;
		}
		else{
			x=array[i+1][j+1];
			x+=mismatch;
		}
		return x;
	}
	
	
}
