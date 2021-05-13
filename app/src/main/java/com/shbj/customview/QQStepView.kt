package com.shbj.customview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat


/*
 *  @项目名：  CustomView 
 *  @包名：    com.shbj.customview
 *  @文件名:   QQStepView
 *  @创建者:   shbj
 *  @创建时间:  2021/4/16 0:31
 *  @描述：    TODO
 */
class QQStepView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val DEFAULT_SIZE = 20
    private var mTextSize: Int
    private var mTextColor: Int
    private var mOuterColor: Int
    private var mInnerColor: Int
    private var mStrokeWidth: Float = 25f
    private val mArcPaint: Paint
    private val mTextPaint: Paint
    private var mCurentStep: Int = 0
    private var mMaxStep: Int = 0

    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.QQStepView)
        mTextSize =
            array.getDimensionPixelSize(R.styleable.QQStepView_qqTextSize, sp2px(DEFAULT_SIZE))
        mTextColor = array.getColor(R.styleable.QQStepView_qqTextColor, Color.BLACK)
        mOuterColor = array.getColor(
            R.styleable.QQStepView_outerColor,
            ContextCompat.getColor(context, R.color.colorPrimary)
        )
        mInnerColor = array.getColor(
            R.styleable.QQStepView_innerColor,
            ContextCompat.getColor(context, R.color.colorAccent)
        )
        array.recycle()
        mArcPaint = Paint()
        mArcPaint.isAntiAlias = true
        mArcPaint.setColor(mOuterColor)
        mArcPaint.style = Paint.Style.STROKE
        mArcPaint.strokeWidth = mStrokeWidth
        mArcPaint.strokeCap = Paint.Cap.ROUND
        mTextPaint = Paint()
        mTextPaint.isAntiAlias = true
        mTextPaint.setColor(mTextColor)
        mTextPaint.textSize = mTextSize.toFloat()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var widthSize = MeasureSpec.getSize(widthMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val realWidth = if (widthSize >= heightSize) widthSize else height
        setMeasuredDimension(realWidth, realWidth)
    }

    override fun onDraw(canvas: Canvas?) {
        val rectf = RectF(
            0f + mStrokeWidth / 2,
            0f + mStrokeWidth / 2,
            width.toFloat() - mStrokeWidth / 2,
            height.toFloat() - mStrokeWidth / 2
        )
        mArcPaint.color = mOuterColor
        canvas!!.drawArc(rectf, 135f, 270f, false, mArcPaint)
        if (mMaxStep == 0) return
        mArcPaint.color = mInnerColor
        val progress = mCurentStep.toFloat() / mMaxStep.toFloat()
        canvas!!.drawArc(rectf, 135f,  progress * 270f, false, mArcPaint)
        val text = "$mCurentStep"
        val bounds = Rect()
        mTextPaint.getTextBounds(text, 0, text.length, bounds)
        val fontMetrics = mTextPaint.getFontMetrics()
        val baseLine = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom + width / 2
        canvas!!.drawText(
            text,
            (width / 2 - bounds.width() / 2).toFloat(), baseLine, mTextPaint
        )
    }

    fun setMaxStep(maxStep: Int){
        mMaxStep = maxStep
    }

    fun setCurrentStep(currentStep: Int){
        mCurentStep = currentStep
        invalidate()
    }

    private fun sp2px(sp: Int): Int =
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            sp.toFloat(),
            resources.displayMetrics
        ).toInt()
}