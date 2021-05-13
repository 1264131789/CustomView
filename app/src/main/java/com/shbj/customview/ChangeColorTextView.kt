package com.shbj.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.TextView


/*
 *  @项目名：  CustomView 
 *  @包名：    com.shbj.customview
 *  @文件名:   ChangeColorTextView
 *  @创建者:   shbj
 *  @创建时间:  2021/5/7 23:21
 *  @描述：    TODO
 */
class ChangeColorTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {
    private val mOriginPaint: Paint
    private val mChangePaint: Paint

    init {
        val array =
            context.obtainStyledAttributes(attrs, R.styleable.ChangeColorTextView)
        val originColor = array.getColor(R.styleable.ChangeColorTextView_originColor, Color.BLACK)
        val changeColor = array.getColor(R.styleable.ChangeColorTextView_changeColor, Color.BLACK)
        array.recycle()
        mOriginPaint = Paint()
        mOriginPaint.color = originColor
        mOriginPaint.isAntiAlias = true
        mChangePaint = Paint()
        mChangePaint.color = changeColor
        mChangePaint.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas?) {

    }
}