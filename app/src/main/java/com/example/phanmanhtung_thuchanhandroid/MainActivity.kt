package com.example.phanmanhtung_thuchanhandroid

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.phanmanhtung_thuchanhandroid.databinding.ActivityMainBinding
import com.example.phanmanhtung_thuchanhandroid.model.Student
import com.example.phanmanhtung_thuchanhandroid.utils.showConfirmDialog
import com.example.phanmanhtung_thuchanhandroid.utils.toAcademicRanking
import com.example.phanmanhtung_thuchanhandroid.utils.toast

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var currentStudent = Student(
        id = "2415053122347",
        name = "Phan Manh Tung",
        className = "126LTDD02",
        email = "12415053122347@sv.ute.udn.vn",
        phone = "0822323581",
        gpa = 3.8
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindStudentData(currentStudent)
        binding.btnUpdateGpa.setOnClickListener {
            val inputStr = binding.edtNewGpa.text.toString().trim()
            val newGpa = inputStr.toDoubleOrNull()
            if (newGpa == null || newGpa !in 0.0..4.0) {
                binding.edtNewGpa.error =
                    "Vui lòng nhập GPA hợp lệ (0.0 - 4.0)"
                toast(
                    "Điểm GPA không hợp lệ!"
                )
                return@setOnClickListener
            }
            currentStudent = currentStudent.copy(gpa = newGpa)
            bindStudentData(currentStudent)
            toast("Cập nhật điểm thành công!")
        }

        binding.btnCall.setOnClickListener {
            val phoneNumber = currentStudent.phone
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
            startActivity(intent)
        }

        binding.btnDelete.setOnClickListener {

            showConfirmDialog(
                title = "Xác nhận xóa",
                message = "Bạn có chắc chắn muốn xóa hồ sơ sinh viên này không?") {
                binding.cardProfile.visibility = View.GONE
                toast("Đã xóa hồ sơ!")
            }
        }
    }
    private fun bindStudentData(student: Student) {

        with(binding) {
            tvName.text = student.name
            tvStudentId.text = "MSSV: ${student.id} • Lớp: ${student.className}"
            tvPhone.text = "SĐT: ${student.phone}"
            tvGpaBadge.text = "${student.gpa} GPA (${student.gpa.toAcademicRanking()})"
            edtNewGpa.setText(student.gpa.toString())
        }
    }
}