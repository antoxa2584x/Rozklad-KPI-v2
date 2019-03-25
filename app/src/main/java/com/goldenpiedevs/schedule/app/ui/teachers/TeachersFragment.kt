package com.goldenpiedevs.schedule.app.ui.teachers

import android.os.Bundle
import android.view.View
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoTeacherModel
import com.goldenpiedevs.schedule.app.ui.base.BaseFragment
import com.goldenpiedevs.schedule.app.ui.teachers.adapter.TeachersAdapter
import io.realm.OrderedRealmCollection
import kotlinx.android.synthetic.main.teachers_fragment_latout.*

class TeachersFragment : BaseFragment(), TeachersView {

    override fun getFragmentLayout() = R.layout.teachers_fragment_latout

    private lateinit var presenter: TeachersPresenter
    private lateinit var adapter: TeachersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = TeachersImplementation()

        with(presenter) {
            attachView(this@TeachersFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.loadTeachers()
    }

    override fun showTeachersData(data: OrderedRealmCollection<DaoTeacherModel>) {
        adapter = TeachersAdapter(data) {
            presenter.onTeacherClick(it)
        }

        teachers_list.apply {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this@TeachersFragment.context)
            adapter = this@TeachersFragment.adapter
        }
    }
}