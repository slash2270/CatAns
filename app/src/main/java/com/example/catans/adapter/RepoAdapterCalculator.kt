//package com.example.catans.adapter
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.BaseAdapter
//import androidx.fragment.app.Fragment
//import com.example.catans.R
//import com.example.catans.databinding.ItemAirportBinding
//
//class RepoAdapterCalculator(private val fragment: Fragment, private val list: List<String>) : BaseAdapter() {
//
//    private lateinit var itemViewBinding:
//
//    override fun  getView(position: Int, convertView: View?, parent: ViewGroup?): View {
//        var convertView: View? = convertView
//        val viewHolder: ViewHolder
//        // 1.创建视图
//        if (convertView == null) {
//            val inflater: LayoutInflater = LayoutInflater.from(fragment.context)
//            convertView = inflater.inflate(R.layout.item_grid, null)
//            viewHolder = ViewHolder()
//            viewHolder.image = convertView.findViewById(R.id.iv_image)
//            viewHolder.name = convertView.findViewById(R.id.tv_name)
//            viewHolder.price = convertView.findViewById(R.id.tv_price)
//            viewHolder.cartButton = convertView.findViewById(R.id.cartButton)
//            convertView.setTag(viewHolder)
//        } else {
//            viewHolder = convertView.getTag()
//        }
//
//        // 2.给视图进行数据赋值
//        val image: ImageView? = viewHolder.image
//        val name: TextView? = viewHolder.name
//        val price: TextView? = viewHolder.price
//        val goods: Goods = data!![position]
//        image.setImageResource(goods.getImage())
//        name.setText(goods.getName())
//        val priceString = "￥" + goods.getPrice() + "/箱"
//        price.setText(priceString)
//
//        // 3.给按钮绑定点击事件
//        viewHolder.cartButton.setOnClickListener(object : OnClickListener() {
//            fun onClick(v: View?) {
//                // 1.获取点击的商品的名称
//                val message: String = goods.getName() + "已加入购物车！"
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
//            }
//        })
//
//        // 返回View
//        return convertView!!
//    }
//
//    override fun getCount(): Int = list.size
//
//    override fun getItem(position: Int): Any = list[position]
//
//    override fun getItemId(position: Int): Long = position.toLong()
//
//    internal inner class ViewHolder {
//        var image: ImageView? = null
//        var name: TextView? = null
//        val price: TextView? = null
//        val cartButton: ImageButton? = null
//    }
//}
