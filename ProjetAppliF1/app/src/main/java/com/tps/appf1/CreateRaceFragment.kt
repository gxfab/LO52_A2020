package com.tps.appf1

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_createrace.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import java.time.LocalTime as LocalTime1


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class CreateRaceFragment : Fragment() {
    lateinit var cal : Calendar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_createrace, container, false)

        //Create the Race db
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Verifie que les champs date et heure sont bien remplis lors du clic
        view.findViewById<Button>(R.id.button_CreateRace2).setOnClickListener {
            if(editText_Date.text.isNotEmpty() && editText_Heure.text.isNotEmpty()){
                //Update Race db
                findNavController().navigate(R.id.action_CreateRaceFragment_to_CreateTeamFragment)
            }
            else
            {
                Toast.makeText(context, "Les champs date et heure sont obligatoire", Toast.LENGTH_SHORT).show()
            }
        }

        //pré-rempli la date du jour sur le champ date
        cal = Calendar.getInstance()
        editText_Date.setText(SimpleDateFormat("dd.MM.yyyy").format(System.currentTimeMillis()))
        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd.MM.yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.FRANCE)
            editText_Date.setText(sdf.format(cal.time))
        }

        //pré-rempli l'heure du jour sur le champ heure
        var hr = Calendar.getInstance()
        editText_Heure.setText(SimpleDateFormat("HH:mm").format(System.currentTimeMillis()))
        val TimeSetListener = TimePickerDialog.OnTimeSetListener { view, hour, minutes ->
            hr.set(Calendar.HOUR_OF_DAY, hour)
            hr.set(Calendar.MINUTE, minutes)

            val myHFormat = "HH:mm"
            val sdfg = SimpleDateFormat(myHFormat, Locale.FRANCE)
            editText_Heure.setText(sdfg.format(hr.time))
        }
    }
}