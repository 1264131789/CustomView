package com.shbj.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View


/*
 *  @项目名：  CustomView 
 *  @包名：    com.shbj.customview
 *  @文件名:   BaseLineView
 *  @创建者:   shbj
 *  @创建时间:  2021/5/13 3:27
 *  @描述：    基线例子
 */
class BaseLineView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val mLinePaint: Paint
    private val mTextPain: Paint
    private val mText = "MfgiA"

    init {
        mLinePaint = Paint()
        mLinePaint.color = Color.RED
        mLinePaint.isAntiAlias = true
        mLinePaint.strokeWidth = 2f
        mTextPain = Paint(Paint.ANTI_ALIAS_FLAG)
        mTextPain.color = Color.BLACK
        mTextPain.textSize = sp2px(50f)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(widthMeasureSpec)
        var heightSize = MeasureSpec.getSize(widthMeasureSpec)
        if (widthMode == MeasureSpec.AT_MOST) {
            val bounds = Rect()
            mTextPain.getTextBounds(mText, 0, mText!!.length, bounds)
            widthSize = bounds.width() + paddingLeft + paddingRight
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            val bounds = Rect()
            mTextPain.getTextBounds(mText, 0, mText!!.length, bounds)
            heightSize = bounds.height() + paddingTop + paddingBottom
        }
        setMeasuredDimension(widthSize, heightSize)
    }

    private fun sp2px(sp: Float): Float =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, resources.displayMetrics)


    override fun onDraw(canvas: Canvas) {
        val metrics = mTextPain.fontMetrics
        val top = metrics.top
        val bottom = metrics.bottom
        val ascent = metrics.ascent
        val descent = metrics.descent
        Log.d("onDraw", "top = $top , bottom = $bottom, ascent = $ascent, descent = $descent")

        //以基线为基准的文字区域，红色线条间，假设基线的坐标为paddingTop，所以显示时文字的基线和View内容的上边界重合
        mLinePaint.color = Color.RED
        canvas.drawLine(
            paddingStart.toFloat(),
            top + paddingTop,
            width.toFloat(),
            top + paddingTop,
            mLinePaint
        )
        canvas.drawLine(
            paddingStart.toFloat(),
            bottom + paddingTop,
            width.toFloat(),
            bottom + paddingTop,
            mLinePaint
        )
        //获取文字中线和基线间的距离
        val dy = (descent - ascent) / 2 - descent
        //绘制中线，绿色，中线坐标 = 基线坐标 - dy = paddingTop - dy
        mLinePaint.color = Color.GREEN
        canvas.drawLine(
            paddingStart.toFloat(),
            paddingTop - dy,
            width.toFloat(),
            paddingTop - dy,
            mLinePaint
        )
        canvas.drawText(mText, paddingStart.toFloat(), paddingTop.toFloat(), mTextPain)

        //View内容区域，黑色线条间
        mLinePaint.color = Color.BLACK
        canvas.drawLine(
            paddingStart.toFloat(),
            paddingTop.toFloat(),
            width.toFloat(),
            paddingTop.toFloat(),
            mLinePaint
        )
        canvas.drawLine(
            paddingStart.toFloat(),
            height.toFloat() - paddingBottom,
            width.toFloat(),
            height.toFloat() - paddingBottom,
            mLinePaint
        )
        //绘制View中线，蓝色，View中线 = height.toFloat() / 2
        mLinePaint.color = Color.BLUE
        canvas.drawLine(
            paddingStart.toFloat(),
            height.toFloat() / 2,
            width.toFloat(),
            height.toFloat() / 2,
            mLinePaint
        )
        //将文字基线映射到View中，由于文字中线坐标 = View中线坐标，所以基线坐标 = View中线 + dy = height.toFloat() / 2 + dy，基线深红色
        mLinePaint.color = Color.MAGENTA
        canvas.drawLine(
            paddingStart.toFloat(),
            height.toFloat() / 2 + dy,
            width.toFloat(),
            height.toFloat() / 2 + dy,
            mLinePaint
        )
        canvas.drawText(mText, paddingStart.toFloat(), height.toFloat() / 2 + dy, mTextPain)
    }
}