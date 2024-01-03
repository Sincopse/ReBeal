package ipca.project.rebeal.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import ipca.project.rebeal.R
import ipca.project.rebeal.databinding.FragmentProfileBinding
import ipca.project.rebeal.ui.Post
import ipca.project.rebeal.ui.toShortDateTime
import java.util.Date

class ProfileFragment : Fragment() {

    var posts : List<Post> = arrayListOf(
        Post("Artur", "O Rui é Gay", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://en.wikipedia.org/wiki/African_wild_dog", Date()),
        Post("Artur", "O Rui é Gay", "https://www.youtube.com/watch?v=dQw4w9WgXcQ", "https://en.wikipedia.org/wiki/African_wild_dog", Date())
    )
    val postsAdapter = PostsListAdapter()

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.GridViewProfilePosts.adapter = postsAdapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class PostsListAdapter : BaseAdapter() {

        override fun getCount(): Int {
            return posts.size
        }

        override fun getItem(position: Int): Any {
            return posts[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rootView = layoutInflater.inflate(R.layout.cell_post,parent,false)
            val textViewDescription = rootView.findViewById<TextView>(R.id.textViewGridTitle)
            val imageView = rootView.findViewById<ImageView>(R.id.imageViewPost)

            textViewDescription.text = posts[position].description

            return rootView
        }

    }
}