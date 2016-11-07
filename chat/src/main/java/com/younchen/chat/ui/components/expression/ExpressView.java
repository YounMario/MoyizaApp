package com.younchen.chat.ui.components.expression;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;

import com.younchen.chat.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ExpressView extends ViewPager {

	private Context context;
	private EditText editText;

	private List<String> reslist;

	public ExpressView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public ExpressView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public void init(int page) {

		// 表情list
		reslist = getExpressionRes(SmileUtils.getLenth());
		// 初始化表情viewpager
		List<View> views = new ArrayList<View>();
		for (int i = 0; i < page; i++) {
			View view = getGridChildView(i, 23);
			views.add(view);
		}
		this.setAdapter(new ExpressionPagerAdapter(views));
	}

	/**
	 * 设置editText
	 * 
	 * @param editText
	 */
	public void setEditTextView(EditText editText) {
		this.editText = editText;
	}

	/**
	 * 获取表情的gridview的子view
	 * 
	 * @param i
	 * @return
	 */
	private View getGridChildView(int page, int count) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.expression_gridview, null);
		ExpandGridView gv = (ExpandGridView) view.findViewById(R.id.gridview);
		List<String> list = new ArrayList<String>();

		int resSize = reslist.size();

		int index = page * count;
		int remain = resSize - index;
		if (remain > count) {
			list.addAll(reslist.subList(index, index + count));
		} else {
			list.addAll(reslist.subList(index, index + remain));
		}

		list.add("delete_expression");
		final ExpressionAdapter expressionAdapter = new ExpressionAdapter(
				context, 1, list);
		gv.setAdapter(expressionAdapter);
		gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String filename = expressionAdapter.getItem(position);
				try {
					// 文字输入框可见时，才可输入表情
					if (filename != "delete_expression") { // 不是删除键，显示表情
						// 这里用的反射，所以混淆的时候不要混淆SmileUtils这个类
						Class clz = Class
								.forName("com.hlb.allcabinet.widget.expression.SmileUtils");
						Field field = clz.getField(filename);
						editText.getText().insert(
								editText.getSelectionStart(),
								SmileUtils.getSmiledText(context,
										(String) field.get(null)));
					} else { // 删除文字或者表情
						if (!TextUtils.isEmpty(editText.getText())) {

							int selectionStart = editText.getSelectionStart();// 获取光标的位置
							if (selectionStart > 0) {
								String body = editText.getText().toString();
								String tempStr = body.substring(0,
										selectionStart);
								int i = tempStr.lastIndexOf("[");// 获取最后一个表情的位置
								if (i != -1) {
									CharSequence cs = tempStr.substring(i,
											selectionStart);
									if (SmileUtils.containsKey(cs.toString()))
										editText.getEditableText().delete(i,
												selectionStart);
									else
										editText.getEditableText().delete(
												selectionStart - 1,
												selectionStart);
								} else {
									editText.getEditableText().delete(
											selectionStart - 1, selectionStart);
								}
							}
						}

					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		return view;
	}

	public List<String> getExpressionRes(int getSum) {
		List<String> reslist = new ArrayList<String>();
		for (int x = 1; x <= getSum; x++) {
			String filename = "ee_" + x;
			reslist.add(filename);
		}
		return reslist;
	}

}
