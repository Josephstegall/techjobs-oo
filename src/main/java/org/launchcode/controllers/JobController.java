package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        Job someJob = jobData.findById(id);
        model.addAttribute("someJob", someJob);

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.
        if (errors.hasErrors()){
            model.addAttribute(jobForm);
            //model.addAttribute("errors", errors);
            return "new-job";
        }
        String nameadd = jobForm.getName();
        Employer employeradd = jobData.getEmployers().findById(jobForm.getEmployerId());
        Location locationadd =  jobData.getLocations().findById(jobForm.getLocationId());
        CoreCompetency competencyadd =  jobData.getCoreCompetencies().findById(jobForm.getCoreCompetencyId());
        PositionType positionadd =  jobData.getPositionTypes().findById(jobForm.getPositionTypeId());

        Job newjob = new Job(nameadd, employeradd, locationadd,
                positionadd, competencyadd);

        jobData.add(newjob);
        model.addAttribute("someJob", newjob);

        return "redirect:/job?id=" + (newjob.getId());

    }
}
