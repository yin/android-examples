package sk.yin.myapp;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.security.*;
import sk.yin.myapp.MainActivity.*;

public class MainActivity extends ListActivity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{   final int itemId = android.R.layout.two_line_list_item;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		ListAdapter adapter = new ArrayAdapter<HashedString>(this,
		    itemId,
		    new HashedString[] {hs("one"),hs("two")}) {
			    @Override
			    public View getView(int position, View convertView, ViewGroup parent) {
					LayoutInflater inflater = (LayoutInflater)getContext()
				       .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					View listItemView = convertView;
					if (null == convertView) {
						listItemView = inflater.inflate(itemId, parent, false);
					}
					// The ListItemLayout must use the standard text item IDs.
					TextView lineOneView = (TextView)listItemView.findViewById(android.R.id.text1);
					TextView lineTwoView = (TextView)listItemView.findViewById(android.R.id.text2);
					HashedString t = (HashedString)getItem(position);
					lineOneView.setText(lineOneText(t));
					lineTwoView.setText(lineTwoText(t));
					return listItemView;
				}

				private CharSequence lineOneText(HashedString t) {
					return t.getStr();
				}
				
				private CharSequence lineTwoText(MainActivity.HashedString t) {
					return t.getSha1();
				}
			};
		setListAdapter(adapter);
	}
	
	@Override
	public void onStart() {
		super.onStart();

	}
	
	private HashedString hs(String s) {
		return new HashedString(s);
	}
	
	public static class HashedString
	{
		private String str;

		private static final String MSG_CANT_DO_HASH = "No hash available...";
		
		public HashedString() {}
		
		public HashedString(String str) {
			this.str = str;
		}

		public String getSha1()
		{
			try
			{
				MessageDigest md = MessageDigest.getInstance("SHA1");
				md.update(str.getBytes());
				byte[] hash = md.digest();
				//TODO: Cache this.
				return toHex(hash);
			}
			catch (NoSuchAlgorithmException e)
			{
				return MSG_CANT_DO_HASH;
			}
		}

		private String toHex(byte[] hash)
		{
			StringBuilder sb = new StringBuilder();
			for (byte b : hash) {
				int u = (b >> 4) & 0x0F,
				    l = b & 0x0F;
				char uhex = toHex(u),
				    lhex = toHex(l);
			    sb.append(uhex).append(lhex);
			}
			return sb.toString();
		}
		private char toHex(int i) {
			return (char) (i < 10 ? '0' + i : 'a' + (i % 10));
		}
		
		public void setStr(String str)
		{
			this.str = str;
		}

		public String getStr()
		{
			return str;
		}
		public String toString() {
			return str+"/"+getSha1();
		}
	}
}

