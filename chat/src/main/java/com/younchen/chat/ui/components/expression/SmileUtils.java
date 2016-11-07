/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.younchen.chat.ui.components.expression;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.Spannable.Factory;
import android.text.style.ImageSpan;

import com.younchen.chat.R;
import com.younchen.utils.DimenUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SmileUtils {

    public static final String ee_1 = "[(1)]";
    public static final String ee_2 = "[(2)]";
    public static final String ee_3 = "[(3)]";
    public static final String ee_4 = "[(4)]";
    public static final String ee_5 = "[(5)]";
    public static final String ee_6 = "[(6)]";
    public static final String ee_7 = "[(7)]";
    public static final String ee_8 = "[(8)]";
    public static final String ee_9 = "[(9)]";
    public static final String ee_10 = "[(10)]";
    public static final String ee_11 = "[(11)]";
    public static final String ee_12 = "[(12)]";
    public static final String ee_13 = "[(13)]";
    public static final String ee_14 = "[(14)]";
    public static final String ee_15 = "[(15)]";
    public static final String ee_16 = "[(16)]";
    public static final String ee_17 = "[(17)]";
    public static final String ee_18 = "[(18)]";
    public static final String ee_19 = "[(19)]";
    public static final String ee_20 = "[(20)]";
    public static final String ee_21 = "[(21)]";
    public static final String ee_22 = "[(22)]";
    public static final String ee_23 = "[(23)]";
    public static final String ee_24 = "[(24)]";
    public static final String ee_25 = "[(25)]";
    public static final String ee_26 = "[(26)]";
    public static final String ee_27 = "[(27)]";
    public static final String ee_28 = "[(28)]";
    public static final String ee_29 = "[(29)]";
    public static final String ee_30 = "[(30)]";
    public static final String ee_31 = "[(31)]";
    public static final String ee_32 = "[(32)]";
    public static final String ee_33 = "[(33)]";
    public static final String ee_34 = "[(34)]";
    public static final String ee_35 = "[(35)]";
    public static final String ee_36 = "[(36)]";
    public static final String ee_37 = "[(37)]";
    public static final String ee_38 = "[(38)]";
    public static final String ee_39 = "[(39)]";
    public static final String ee_40 = "[(40)]";
    public static final String ee_41 = "[(41)]";
    public static final String ee_42 = "[(42)]";
    public static final String ee_43 = "[(43)]";
    public static final String ee_44 = "[(44)]";
    public static final String ee_45 = "[(45)]";
    public static final String ee_46 = "[(46)]";
    public static final String ee_47 = "[(47)]";
    public static final String ee_48 = "[(48)]";
    public static final String ee_49 = "[(49)]";
    public static final String ee_50 = "[(50)]";
    public static final String ee_51 = "[(51)]";
    public static final String ee_52 = "[(52)]";
    public static final String ee_53 = "[(53)]";
    public static final String ee_54 = "[(54)]";
    public static final String ee_55 = "[(55)]";
    public static final String ee_56 = "[(56)]";
    public static final String ee_57 = "[(57)]";
    public static final String ee_58 = "[(58)]";
    public static final String ee_59 = "[(59)]";
    public static final String ee_60 = "[(60)]";
    public static final String ee_61 = "[(61)]";
    public static final String ee_62 = "[(62)]";
    public static final String ee_63 = "[(63)]";
    public static final String ee_64 = "[(64)]";
    public static final String ee_65 = "[(65)]";
    public static final String ee_66 = "[(66)]";
    public static final String ee_67 = "[(67)]";
    public static final String ee_68 = "[(68)]";
    public static final String ee_69 = "[(69)]";
    public static final String ee_70 = "[(70)]";
    public static final String ee_71 = "[(71)]";
    public static final String ee_72 = "[(72)]";
    public static final String ee_73 = "[(73)]";
    public static final String ee_74 = "[(74)]";
    public static final String ee_75 = "[(75)]";
    public static final String ee_76 = "[(76)]";
    public static final String ee_77 = "[(77)]";
    public static final String ee_78 = "[(78)]";
    public static final String ee_79 = "[(79)]";
    public static final String ee_80 = "[(80)]";
    public static final String ee_81 = "[(81)]";
    public static final String ee_82 = "[(82)]";
    public static final String ee_83 = "[(83)]";
    public static final String ee_84 = "[(84)]";
    public static final String ee_85 = "[(85)]";
    public static final String ee_86 = "[(86)]";
    public static final String ee_87 = "[(87)]";
    public static final String ee_88 = "[(88)]";
    public static final String ee_89 = "[(89)]";
    public static final String ee_90 = "[(90)]";
    public static final String ee_91 = "[(91)]";
    public static final String ee_92 = "[(92)]";
    public static final String ee_93 = "[(93)]";
    public static final String ee_94 = "[(94)]";
    public static final String ee_95 = "[(95)]";
    public static final String ee_96 = "[(96)]";
    public static final String ee_97 = "[(97)]";
    public static final String ee_98 = "[(98)]";
    public static final String ee_99 = "[(99)]";
    public static final String ee_100 = "[(100)]";
    public static final String ee_101 = "[(101)]";
    public static final String ee_102 = "[(102)]";
    public static final String ee_103 = "[(103)]";
    public static final String ee_104 = "[(104)]";
    public static final String ee_105 = "[(105)]";
    public static final String ee_106 = "[(106)]";
    public static final String ee_107 = "[(107)]";
    public static final String ee_108 = "[(108)]";
    public static final String ee_109 = "[(109)]";
    public static final String ee_110 = "[(110)]";
    public static final String ee_111 = "[(111)]";
    public static final String ee_112 = "[(112)]";

    private static final Factory spannableFactory = Factory
            .getInstance();

    private static final Map<Pattern, Integer> emoticons = new HashMap<Pattern, Integer>();

    static {
        addPattern(emoticons, ee_1, R.mipmap.ee_1);
        addPattern(emoticons, ee_2, R.mipmap.ee_2);
        addPattern(emoticons, ee_3, R.mipmap.ee_3);
        addPattern(emoticons, ee_4, R.mipmap.ee_4);
        addPattern(emoticons, ee_5, R.mipmap.ee_5);
        addPattern(emoticons, ee_6, R.mipmap.ee_6);
        addPattern(emoticons, ee_7, R.mipmap.ee_7);
        addPattern(emoticons, ee_8, R.mipmap.ee_8);
        addPattern(emoticons, ee_9, R.mipmap.ee_9);
        addPattern(emoticons, ee_10, R.mipmap.ee_10);
        addPattern(emoticons, ee_11, R.mipmap.ee_11);
        addPattern(emoticons, ee_12, R.mipmap.ee_12);
        addPattern(emoticons, ee_13, R.mipmap.ee_13);
        addPattern(emoticons, ee_14, R.mipmap.ee_14);
        addPattern(emoticons, ee_15, R.mipmap.ee_15);
        addPattern(emoticons, ee_16, R.mipmap.ee_16);
        addPattern(emoticons, ee_17, R.mipmap.ee_17);
        addPattern(emoticons, ee_18, R.mipmap.ee_18);
        addPattern(emoticons, ee_19, R.mipmap.ee_19);
        addPattern(emoticons, ee_20, R.mipmap.ee_20);
        addPattern(emoticons, ee_21, R.mipmap.ee_21);
        addPattern(emoticons, ee_22, R.mipmap.ee_22);
        addPattern(emoticons, ee_23, R.mipmap.ee_23);
        addPattern(emoticons, ee_24, R.mipmap.ee_24);
        addPattern(emoticons, ee_25, R.mipmap.ee_25);
        addPattern(emoticons, ee_26, R.mipmap.ee_26);
        addPattern(emoticons, ee_27, R.mipmap.ee_27);
        addPattern(emoticons, ee_28, R.mipmap.ee_28);
        addPattern(emoticons, ee_29, R.mipmap.ee_29);
        addPattern(emoticons, ee_30, R.mipmap.ee_30);
        addPattern(emoticons, ee_31, R.mipmap.ee_31);
        addPattern(emoticons, ee_32, R.mipmap.ee_32);
        addPattern(emoticons, ee_33, R.mipmap.ee_33);
        addPattern(emoticons, ee_34, R.mipmap.ee_34);
        addPattern(emoticons, ee_35, R.mipmap.ee_35);
        addPattern(emoticons, ee_36, R.mipmap.ee_36);
        addPattern(emoticons, ee_37, R.mipmap.ee_37);
        addPattern(emoticons, ee_38, R.mipmap.ee_38);
        addPattern(emoticons, ee_39, R.mipmap.ee_39);
        addPattern(emoticons, ee_40, R.mipmap.ee_40);
        addPattern(emoticons, ee_41, R.mipmap.ee_41);
        addPattern(emoticons, ee_42, R.mipmap.ee_42);
        addPattern(emoticons, ee_43, R.mipmap.ee_43);
        addPattern(emoticons, ee_44, R.mipmap.ee_44);
        addPattern(emoticons, ee_45, R.mipmap.ee_45);
        addPattern(emoticons, ee_46, R.mipmap.ee_46);
        addPattern(emoticons, ee_47, R.mipmap.ee_47);
        addPattern(emoticons, ee_48, R.mipmap.ee_48);
        addPattern(emoticons, ee_49, R.mipmap.ee_49);
        addPattern(emoticons, ee_50, R.mipmap.ee_50);
        addPattern(emoticons, ee_51, R.mipmap.ee_51);
        addPattern(emoticons, ee_52, R.mipmap.ee_52);
        addPattern(emoticons, ee_53, R.mipmap.ee_53);
        addPattern(emoticons, ee_54, R.mipmap.ee_54);
        addPattern(emoticons, ee_55, R.mipmap.ee_55);
        addPattern(emoticons, ee_56, R.mipmap.ee_56);
        addPattern(emoticons, ee_57, R.mipmap.ee_57);
        addPattern(emoticons, ee_58, R.mipmap.ee_58);
        addPattern(emoticons, ee_59, R.mipmap.ee_59);
        addPattern(emoticons, ee_60, R.mipmap.ee_60);
        addPattern(emoticons, ee_61, R.mipmap.ee_61);
        addPattern(emoticons, ee_62, R.mipmap.ee_62);
        addPattern(emoticons, ee_63, R.mipmap.ee_63);
        addPattern(emoticons, ee_64, R.mipmap.ee_64);
        addPattern(emoticons, ee_65, R.mipmap.ee_65);
        addPattern(emoticons, ee_66, R.mipmap.ee_66);
        addPattern(emoticons, ee_67, R.mipmap.ee_67);
        addPattern(emoticons, ee_68, R.mipmap.ee_68);
        addPattern(emoticons, ee_69, R.mipmap.ee_69);
        addPattern(emoticons, ee_70, R.mipmap.ee_70);
        addPattern(emoticons, ee_71, R.mipmap.ee_71);
        addPattern(emoticons, ee_72, R.mipmap.ee_72);
        addPattern(emoticons, ee_73, R.mipmap.ee_73);
        addPattern(emoticons, ee_74, R.mipmap.ee_74);
        addPattern(emoticons, ee_75, R.mipmap.ee_75);
        addPattern(emoticons, ee_76, R.mipmap.ee_76);
        addPattern(emoticons, ee_77, R.mipmap.ee_77);
        addPattern(emoticons, ee_78, R.mipmap.ee_78);
        addPattern(emoticons, ee_79, R.mipmap.ee_79);
        addPattern(emoticons, ee_80, R.mipmap.ee_80);
        addPattern(emoticons, ee_81, R.mipmap.ee_81);
        addPattern(emoticons, ee_82, R.mipmap.ee_82);
        addPattern(emoticons, ee_83, R.mipmap.ee_83);
        addPattern(emoticons, ee_84, R.mipmap.ee_84);
        addPattern(emoticons, ee_85, R.mipmap.ee_85);
        addPattern(emoticons, ee_86, R.mipmap.ee_86);
        addPattern(emoticons, ee_87, R.mipmap.ee_87);
        addPattern(emoticons, ee_88, R.mipmap.ee_88);
        addPattern(emoticons, ee_89, R.mipmap.ee_89);
        addPattern(emoticons, ee_90, R.mipmap.ee_90);
        addPattern(emoticons, ee_91, R.mipmap.ee_91);
        addPattern(emoticons, ee_92, R.mipmap.ee_92);
        addPattern(emoticons, ee_93, R.mipmap.ee_93);
        addPattern(emoticons, ee_94, R.mipmap.ee_94);
        addPattern(emoticons, ee_95, R.mipmap.ee_95);
        addPattern(emoticons, ee_96, R.mipmap.ee_96);
        addPattern(emoticons, ee_97, R.mipmap.ee_97);
        addPattern(emoticons, ee_98, R.mipmap.ee_98);
        addPattern(emoticons, ee_99, R.mipmap.ee_99);
        addPattern(emoticons, ee_100, R.mipmap.ee_100);
        addPattern(emoticons, ee_101, R.mipmap.ee_101);
        addPattern(emoticons, ee_102, R.mipmap.ee_102);
        addPattern(emoticons, ee_103, R.mipmap.ee_103);
        addPattern(emoticons, ee_104, R.mipmap.ee_104);
        addPattern(emoticons, ee_105, R.mipmap.ee_105);
        addPattern(emoticons, ee_106, R.mipmap.ee_106);
        addPattern(emoticons, ee_107, R.mipmap.ee_107);
        addPattern(emoticons, ee_108, R.mipmap.ee_108);
        addPattern(emoticons, ee_109, R.mipmap.ee_109);
        addPattern(emoticons, ee_110, R.mipmap.ee_110);
        addPattern(emoticons, ee_111, R.mipmap.ee_111);
        addPattern(emoticons, ee_112, R.mipmap.ee_112);

    }

    private static void addPattern(Map<Pattern, Integer> map, String smile,
                                   int resource) {
        map.put(Pattern.compile(Pattern.quote(smile)), resource);
    }

    /**
     * replace existing spannable with smiles
     *
     * @param context
     * @param spannable
     * @return
     */
    public static boolean addSmiles(Context context, Spannable spannable) {
        boolean hasChanges = false;
        for (Entry<Pattern, Integer> entry : emoticons.entrySet()) {
            Matcher matcher = entry.getKey().matcher(spannable);
            while (matcher.find()) {
                boolean set = true;
                for (ImageSpan span : spannable.getSpans(matcher.start(),
                        matcher.end(), ImageSpan.class))
                    if (spannable.getSpanStart(span) >= matcher.start()
                            && spannable.getSpanEnd(span) <= matcher.end())
                        spannable.removeSpan(span);
                    else {
                        set = false;
                        break;
                    }
                if (set) {
                    hasChanges = true;
                    Drawable drawable = context.getResources().getDrawable(
                            entry.getValue());
                    int bounds = DimenUtil.dip2px(context, 20);
                    drawable.setBounds(0, 0, bounds, bounds);// 这里设置图片的大小
                    ImageSpan imageSpan = new ImageSpan(drawable);
                    spannable.setSpan(imageSpan, matcher.start(),
                            matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
        return hasChanges;
    }

    public static Spannable getSmiledText(Context context, CharSequence text) {
        Spannable spannable = spannableFactory.newSpannable(text);
        addSmiles(context, spannable);
        return spannable;
    }

    public static Spannable getSmiledTextSmallImg(Context context,
                                                  CharSequence text) {
        Spannable spannable = spannableFactory.newSpannable(text);
        for (Entry<Pattern, Integer> entry : emoticons.entrySet()) {
            Matcher matcher = entry.getKey().matcher(spannable);
            while (matcher.find()) {
                boolean set = true;
                for (ImageSpan span : spannable.getSpans(matcher.start(),
                        matcher.end(), ImageSpan.class))
                    if (spannable.getSpanStart(span) >= matcher.start()
                            && spannable.getSpanEnd(span) <= matcher.end())
                        spannable.removeSpan(span);
                    else {
                        set = false;
                        break;
                    }
                if (set) {
                    Bitmap bitmap = null;
                    bitmap = BitmapFactory.decodeResource(
                            context.getResources(), entry.getValue());

                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    Matrix matrix = new Matrix();
                    // 缩放图片动作
                    matrix.postScale(0.50f, 0.50f);
                    Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                            width, height, matrix, true);
                    ImageSpan imageSpan = new ImageSpan(context, resizedBitmap);
                    // bitmap.recycle();
                    spannable.setSpan(imageSpan, matcher.start(),
                            matcher.end(), Spannable.SPAN_COMPOSING);
                    try {
                        bitmap.recycle();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            }
        }

        return spannable;
    }

    public static boolean containsKey(String key) {
        boolean b = false;
        for (Entry<Pattern, Integer> entry : emoticons.entrySet()) {
            Matcher matcher = entry.getKey().matcher(key);
            if (matcher.find()) {
                b = true;
                break;
            }
        }

        return b;
    }

    public static int getLenth() {
        // TODO Auto-generated method stub
        return 112;
    }

}
