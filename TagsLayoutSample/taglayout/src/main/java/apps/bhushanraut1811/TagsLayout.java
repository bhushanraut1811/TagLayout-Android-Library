package apps.bhushanraut1811;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by bhushan.raut on 8/6/2016.
 */
public class TagsLayout extends LinearLayout {
    /**
     * These are used for computing child frames based on their gravity.
     */
    private final Rect mTmpContainerRect = new Rect();


    public TagsLayout(Context context) {
        super(context);
    }

    public TagsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Any layout manager that doesn't scroll will want this.
     */
    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    /**
     * Ask all children to measure themselves and compute the measurement of this
     * layout based on the children.
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //no of child present in the layout
        int count = getChildCount();
        int desiredWidth = 300;//dummy
        int desiredHeight = 500;//dummy

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int layoutWidth;
        int layoutHeight;


        //Measure  Layout Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size: Fixed size
            layoutWidth = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than: Wrap Content
            //find max childWith and set is as Width of layout
            int maxChildWithInLayout = 0;
            int cW;
            for (int i = 0; i < count; i++) {
                final View c = getChildAt(i);
                if (c.getVisibility() != GONE) {
                    measureChildWithMargins(c, widthMeasureSpec, 0, heightMeasureSpec, 0);
                    final LayoutParams lp = (LayoutParams) c.getLayoutParams();
                    cW = c.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
                    if (cW > maxChildWithInLayout) {
                        maxChildWithInLayout = cW;
                    }
                }
            }
            layoutWidth = maxChildWithInLayout;
        } else {
            //Be whatever you want: Match Parent or custom
            layoutWidth = desiredWidth;
        }

        //Measure Layout Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size:Fixed size
            layoutHeight = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            //calculate layoutHeight
            layoutHeight = Math.min(desiredHeight, heightSize);
        } else {
            //Be whatever you want
            layoutHeight = desiredHeight;
        }

        // Measurement will ultimately be computing these values.
        int maxHeight = 0;//layout max height
        int maxWidth = 0;//layout max Width
        int childState = 0;

        // Iterate through all children, measuring them and computing layout dimensions from their size.
        int childWidth, childHeight = 0;
        for (int i = 0; i < count; i++) {
            childWidth = 0;
            childHeight = 0;
            final View child = getChildAt(i);

            if (child.getVisibility() != GONE) {
                // Measure the child with its margins.
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);

                //calculate Box
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
                childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

                if (maxWidth + childWidth < layoutWidth) {
                    maxWidth += childWidth;

                } else {
                    maxWidth = 0;
                    maxWidth += childWidth;
                    maxHeight += childHeight;
                }

                childState = combineMeasuredStates(childState, child.getMeasuredState());
            }
        }
        //for last lane we adding over here
        maxHeight += childHeight;
        //if not Fixed Size then set calculated one as height
        if (!(heightMode == MeasureSpec.EXACTLY))
            layoutHeight = maxHeight;


        // Report our final dimensions.
        setMeasuredDimension(resolveSizeAndState(layoutWidth, widthMeasureSpec, childState),
                resolveSizeAndState(layoutHeight, heightMeasureSpec,
                        childState << MEASURED_HEIGHT_STATE_SHIFT));
    }

    /**
     * Position all children within this layout.
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int count = getChildCount();

        // These are the far left and right edges in which we are performing layout.
        int leftPos = getPaddingLeft();
        int rightPos = right - left - getPaddingRight();

        int maxWidthLimit = rightPos;

        // These are the top and bottom edges in which we are performing layout.
        final int parentTop = getPaddingTop();
        final int parentBottom = bottom - top - getPaddingBottom();

        int maxHeightLimit = parentBottom;

        int laneWidth = 0;
        int laneBaseHeight = 0;
        int laneHeight = 0;

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {

                final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                final int width = child.getMeasuredWidth() + lp.rightMargin + lp.leftMargin;
                final int height = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

                if (width + laneWidth < maxWidthLimit) {
                    int previousLaneWidth = laneWidth;
                    laneWidth += width;

                    mTmpContainerRect.left = previousLaneWidth + lp.leftMargin;
                    mTmpContainerRect.right = laneWidth - lp.rightMargin;
                    mTmpContainerRect.top = laneBaseHeight + lp.topMargin;
                    mTmpContainerRect.bottom = laneBaseHeight + height - lp.bottomMargin;

                } else {
                    laneWidth = 0;
                    laneBaseHeight += height;
                    int previousLaneWidth = laneWidth;
                    laneHeight += height;
                    laneWidth += width;

                    mTmpContainerRect.left = previousLaneWidth + lp.leftMargin;
                    mTmpContainerRect.right = laneWidth - lp.rightMargin;
                    mTmpContainerRect.top = laneBaseHeight + lp.topMargin;
                    mTmpContainerRect.bottom = laneBaseHeight + height - lp.bottomMargin;

                }

                // Use the child's gravity and size to determine its final
                // frame within its container.
                // Gravity.apply(lp.gravity, width, height, mTmpContainerRect, mTmpChildRect);

                // Place the child.
                child.layout(mTmpContainerRect.left, mTmpContainerRect.top,
                        mTmpContainerRect.right, mTmpContainerRect.bottom);
            }
        }
    }

}
