package kr.applepi.kotlintest.activities

import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import com.github.nitrico.lastadapter.LastAdapter
import kotlinx.android.synthetic.main.activity_list.*
import kr.applepi.kotlintest.R
import kr.applepi.kotlintest.BR
import kr.applepi.kotlintest.data.*
import kr.applepi.kotlintest.databinding.ActivityIdeaBinding
import kr.applepi.kotlintest.databinding.ActivityListBinding
import kr.applepi.kotlintest.databinding.ItemListBinding
import java.util.zip.Inflater

class ListActivity : BaseAppCompatActivity() {

    val binding: ActivityListBinding by lazy {
        DataBindingUtil.setContentView<ActivityListBinding>(this, R.layout.activity_list)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binding.collapsingToolbarLayout.setExpandedTitleColor(0x00ffffff)

        recycler_list.layoutManager = LinearLayoutManager(this)

        LastAdapter.with(Data.ideaList, BR.item)
                .map<Idea>(R.layout.item_list)
                .onClick {
                    startActivity(Intent(this@ListActivity, IdeaActivity::class.java).apply {
                        putExtra("position", position)
                    })
                }
                .into(recycler_list)


    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
//
//    inner class MyAdapter() : RecyclerView.Adapter<MyAdapter.MyVH>() {
//        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyVH {
//            val binding = LayoutInflater.from(parent?.context).inflate(R.layout.item_list, parent, false)
//            return MyVH(binding)
//        }
//
//        override fun onBindViewHolder(holder: MyVH?, position: Int) {
//            holder?.itemView?.setOnClickListener { startActivity(Intent(this@ListActivity, IdeaActivity::class.java).apply{
//                putExtra("position", position)
//            }) }
//            holder?.binding?.setVariable(BR.item, Data.ideaList.get(position))
//        }
//
//        override fun getItemCount(): Int {
//            return Data.ideaList.size
//        }
//
//        inner class MyVH(item: View) : RecyclerView.ViewHolder(item) {
//            val binding: ViewDataBinding = DataBindingUtil.bind(item)
//        }
//    }
}
