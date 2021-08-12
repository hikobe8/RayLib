package com.ray.recyclerviewdecoration

import android.content.res.Resources
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager = GridLayoutManager(this@MainActivity, 4)
        recyclerView.addItemDecoration(MyItemDecoration())
        recyclerView.adapter = MyAdapter()
    }
}

fun Int.dp2px() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics).toInt()

class MyItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val itemWidth = parent.width / 4f
        val imgWidth = 50.dp2px()
        val childAdapterPosition = parent.getChildAdapterPosition(view)
        if (childAdapterPosition % 4 == 0) {
            outRect.set(40.dp2px(), 0, 10.dp2px(), 20.dp2px())
        } else if (childAdapterPosition % 4 == 1) {
            outRect.set(10.dp2px(), 0, 40.dp2px(), 20.dp2px())
        } else if (childAdapterPosition % 4 == 2) {
            outRect.set(40.dp2px(), 0, 10.dp2px(), 20.dp2px())
        } else if (childAdapterPosition % 4 == 3) {
            outRect.set(10.dp2px(), 0, 40.dp2px(), 20.dp2px())
        }
    }

}

class MyAdapter : RecyclerView.Adapter<MyHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
    }

    override fun getItemCount() = 36

}

class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        fun create(parent: ViewGroup) = MyHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false))
    }

}