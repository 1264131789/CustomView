package com.shbj.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat


/*
 *  @项目名：  CustomView 
 *  @包名：    com.shbj.customview
 *  @文件名:   MyTextView
 *  @创建者:   shbj
 *  @创建时间:  2021/4/15 17:08
 *  @描述：    TODO
 */
class MyTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val DEFAULT_SIZE = 15
    private val mPaint: Paint
    private var mText: String?
    private var mTextSize: Int
    private var mtextColor:Int
    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.MyTextView)
        mText = array.getString(R.styleable.MyTextView_text)
        mTextSize = array.getDimensionPixelSize(R.styleable.MyTextView_textSize, sp2px(DEFAULT_SIZE))
        mtextColor = array.getColor(R.styleable.MyTextView_textColor, ContextCompat.getColor(context, android.R.color.black))
        array.recycle()
        mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.color = mtextColor
        mPaint.textSize = mTextSize.toFloat()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(widthMeasureSpec)
        var heightSize = MeasureSpec.getSize(widthMeasureSpec)
        if (widthMode == MeasureSpec.AT_MOST){
            val bounds = Rect()
            mPaint.getTextBounds(mText, 0, mText!!.length, bounds)
            widthSize = bounds.width()
        }
        if (heightMode == MeasureSpec.AT_MOST){
            val bounds = Rect()
            mPaint.getTextBounds(mText, 0, mText!!.length, bounds)
            heightSize = bounds.height()
        }
        widthSize = widthSize + paddingLeft + paddingRight
        heightSize = heightSize + paddingTop + paddingBottom
        setMeasuredDimension(widthSize, heightSize)
    }

    override fun onDraw(canvas: Canvas?) {
        val metrics = mPaint.fontMetricsInt
        val dy = (metrics.bottom - metrics.top) / 2 - metrics.bottom
        canvas!!.drawText(mText!!, paddingLeft.toFloat(), (height/2).toFloat() + dy, mPaint)
    }

    private fun sp2px(sp:Int): Int =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp.toFloat(), resources.displayMetrics).toInt()
}