package com.example.jgodi.jpapp.ui.user

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jgodi.jpapp.R
import com.example.jgodi.jpapp.domain.User
import com.example.jgodi.jpapp.ui.EndlessScrollListener
import com.example.jgodi.jpapp.ui.recyclerx.BaseAdapter
import com.example.jgodi.jpapp.ui.recyclerx.BaseViewHolder
import com.example.jgodi.jpapp.ui.recyclerx.OnViewHolderClick
import com.example.jgodi.jpapp.ui.toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_users_fragment.*


class ListUsersFragment : Fragment() {

    companion object {
        fun newInstance() = ListUsersFragment()
        private const val PAGE: Int = 1
        private const val PER_PAGE: Int = 10
    }

    private lateinit var adapter: MyAdapter
    private lateinit var endlessScrollListener: EndlessScrollListener
    private lateinit var viewModel: ListUsersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.list_users_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        populateRecycler(PAGE, PER_PAGE)
    }


    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ListUsersViewModel::class.java)
    }

    private fun initUI() {
        val layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL,
                false)

        adapter = MyAdapter()
        endlessScrollListener = EndlessScrollListener(layoutManager,
                object : EndlessScrollListener.ScrollListener {
                    override fun onHide() { }

                    override fun onShow() { }

                    override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                        if(totalItemsCount >= PER_PAGE) {
                            populateRecycler(page + 1, PER_PAGE)
                        }
                    }
                })

        recycler_view_users.layoutManager = layoutManager
        recycler_view_users.setHasFixedSize(true)
        recycler_view_users.addItemDecoration(DividerItemDecoration(this.context!!))
        recycler_view_users.adapter = adapter
        recycler_view_users.addOnScrollListener(endlessScrollListener)

        swipe_refresh_layout.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorPrimaryDark,
                R.color.colorAccent)
        swipe_refresh_layout.setOnRefreshListener {
            adapter.clear()
            endlessScrollListener.resetState()
            populateRecycler(PAGE, PER_PAGE)
//            adapter!!.clear()
//            Handler().postDelayed({
//                swipe_refresh_layout.isRefreshing = false
//                if(!swipe_refresh_layout.isRefreshing) {
//                    populateRecycler()
//                }
//            }, 400)
        }
    }

    private fun populateRecycler(page: Int, perPage: Int) {
        swipe_refresh_layout.isRefreshing = true
        viewModel.getListUsers(page, perPage)?.observe(this, Observer {
            if(adapter.itemCount == 0) {
                adapter.data = it.toMutableList()
                recycler_view_users.layoutAnimation = AnimationUtils
                        .loadLayoutAnimation(context, R.anim.layout_animation_slide_bottom)
                recycler_view_users.adapter!!.notifyDataSetChanged()
                recycler_view_users.scheduleLayoutAnimation()
            } else {
                adapter.addAll(it)
            }

            swipe_refresh_layout.isRefreshing = false
        })
    }


    class MyViewHolder(override val container: View):
            BaseViewHolder<User>(container) {
        private var textViewName: TextView = container.findViewById(R.id.text_view_name)
        private var imageViewAvatar: ImageView = container.findViewById(R.id.image_view_avatar)

        override fun bind(item: User) {
            super.bind(item)

            textViewName.text = item.fullName
            Picasso.get()
                    .load(item.avatar)
                    .resize(80, 80)
                    .centerCrop()
                    .into(imageViewAvatar)
        }
    }

    class MyAdapter:
            BaseAdapter<User>() {

        override fun getLayoutId(position: Int, item: User): Int {
            return R.layout.layout_user_item
        }

        override fun getViewHolder(container: View, viewType: Int): RecyclerView.ViewHolder {
            val context = container.context
            val viewHolder = MyViewHolder(container)
            viewHolder.setClickListener(object: OnViewHolderClick<User> {
                override fun onItemClick(item: User, position: Int) {
                    context.toast(item.fullName, Toast.LENGTH_SHORT)
                }
            })
            return viewHolder
        }
    }



    class DividerItemDecoration(context: Context) : RecyclerView.ItemDecoration() {
        private val divider: Drawable

        companion object {
            private val ATTRS = intArrayOf(android.R.attr.listDivider)
        }

        init {
            val a = context.obtainStyledAttributes(ATTRS)
            divider = a.getDrawable(0)
            a.recycle()
        }

        override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
            drawVertical(c, parent)
        }

        private fun drawVertical(c: Canvas, parent: RecyclerView) {
            val left = parent.paddingLeft
            val right = parent.width - parent.paddingRight

            val childCount = parent.childCount
            for (i in 0 until childCount) {
                val child = parent.getChildAt(i)
                val params = child.layoutParams as RecyclerView.LayoutParams
                val top = child.bottom + params.bottomMargin
                val bottom = top + divider.intrinsicHeight
                divider.setBounds(left, top, right, bottom)
                divider.draw(c)
            }
        }

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            outRect.set(0, 0, 0, divider.intrinsicHeight)
        }
    }

}
