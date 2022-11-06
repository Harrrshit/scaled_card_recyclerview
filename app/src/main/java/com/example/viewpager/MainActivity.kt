package com.example.viewpager

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.viewpager.adapter.RecyclerViewAdapter
import com.example.viewpager.databinding.ActivityMainBinding
import com.example.viewpager.model.DataModel
import kotlin.math.abs

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var snapHelper: SnapHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.apply {
            setSupportActionBar(activityMainToolbar)
            activityMainToolbar.setNavigationOnClickListener {
                Toast.makeText(applicationContext, "clicked", Toast.LENGTH_SHORT).show()
            }
        }
        val imageList = setUpArrayList()
        setUpRecyclerView(imageList)
    }
    private fun setUpArrayList(): ArrayList<DataModel>{
        val imageList: ArrayList<DataModel> = ArrayList()
        imageList.add(
            DataModel(
                R.drawable.image1,
                "Image 1"
            )
        )
        imageList.add(
            DataModel(
                R.drawable.image2,
                "Image 2"
            )
        )
        imageList.add(
            DataModel(
                R.drawable.image3,
                "Image 3"
            )
        )
        imageList.add(
            DataModel(
                R.drawable.image4,
                "Image 4"
            )
        )
        imageList.add(
            DataModel(
                R.drawable.image5,
                "Image 5"
            )
        )
        imageList.add(
            DataModel(
                R.drawable.image6,
                "Image 6"
            )
        )
        imageList.add(
            DataModel(
                R.drawable.image7,
                "Image 7"
            )
        )
        return imageList
    }
    private fun setUpRecyclerView(imageList: ArrayList<DataModel>){
        snapHelper = PagerSnapHelper()
        binding.apply {
            activityMainRecyclerView.layoutManager = ProminentLayoutManager(this@MainActivity)
            val adapter = RecyclerViewAdapter(this@MainActivity, imageList)
            activityMainRecyclerView.adapter = adapter
            val spacing = 8
            activityMainRecyclerView.addItemDecoration(LinearHorizontalSpacingDecoration(spacing))
        }
        snapHelper.attachToRecyclerView(binding.activityMainRecyclerView)
    }

}
class LinearHorizontalSpacingDecoration(private val innerSpacing: Int): RecyclerView.ItemDecoration(){
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val itemPosition = parent.getChildAdapterPosition(view)

        outRect.left = if(itemPosition == 0) 8 else innerSpacing / 2
        outRect.right = if(itemPosition == state.itemCount - 1) 8 else innerSpacing / 2
    }
}
class ProminentLayoutManager(
    context: Context,
    private val minScaleDistanceFactor: Float = 3.0f /*Shrink Distance*/,
    private val scaleDownBy: Float = 0.5f /*Shrink amount*/
): LinearLayoutManager(context, HORIZONTAL, false){
    override fun onLayoutCompleted(state: RecyclerView.State?) {
        super.onLayoutCompleted(state)
        scaleChildren()

    }
    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        if(orientation == HORIZONTAL){
            scaleChildren()
        }
        return super.scrollHorizontallyBy(dx, recycler, state)

    }
    private fun scaleChildren(){
        val containerCenter = width / 2f //mid point
        val scaleDownDistanceThreshold = minScaleDistanceFactor * containerCenter  //Distance 1
        var count = 1
        for(i in 0 until childCount){
            val child = getChildAt(i)!!
            val childCenter = (child.left + child.right) / 2f
            var distanceToCenter = (abs(containerCenter - childCenter)).coerceAtMost(scaleDownDistanceThreshold)
            distanceToCenter *= 0.5f
            val scaleDownCenter = (distanceToCenter / scaleDownDistanceThreshold).coerceAtMost(1f)
            val scale = 1f - scaleDownBy * distanceToCenter / scaleDownDistanceThreshold
            child.scaleX = scale
            child.scaleY = scale
        }
    }
}




/*


class ProminentLayoutManager(
    context: Context,
    private val minScaleDistanceFactor: Float = 4f,
    private val scaleDownBy: Float = .2f): LinearLayoutManager(context, HORIZONTAL, false){
    override fun onLayoutCompleted(state: RecyclerView.State?) {
        super.onLayoutCompleted(state)
        scaleChildren()

    }
    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        if(orientation == HORIZONTAL){
            scaleChildren()
        }
        return super.scrollHorizontallyBy(dx, recycler, state)

    }
    private fun scaleChildren(){
        val containerCenter = width / 2f
        val scaleDownDistanceThreshold = (minScaleDistanceFactor * containerCenter) * .5f

        for(i in 0 until childCount){
            val child = getChildAt(i)!!
            val childCenter = (getDecoratedLeft(child) + getDecoratedRight(child)) / 2f
            val distanceToCenter = abs(childCenter - containerCenter)

            val scaleDownCenter = (distanceToCenter / scaleDownDistanceThreshold).coerceAtMost(1f)
            val scale = 1f - scaleDownBy * scaleDownCenter
            child.scaleX = scale / 1f
            child.scaleY = scale / 1f
        }
    }
}

 */