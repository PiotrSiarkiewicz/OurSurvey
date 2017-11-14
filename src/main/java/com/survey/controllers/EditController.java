package com.survey.controllers;

import com.survey.models.Answer;
import com.survey.models.Question;
import com.survey.models.Survey;
import com.survey.repository.AnswerRepository;
import com.survey.repository.QuestionRepository;
import com.survey.repository.SurveyRepository;
import com.survey.repository.UserRepository;
import com.survey.services.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class EditController
{

    private QuestionRepository questionRepository;
    private AnswerRepository answerRepository;
    private SurveyService surveyService;
    private Survey survey;


    @Autowired
    public EditController(
                    QuestionRepository questionRepository,
                    AnswerRepository answerRepository, SurveyService surveyService )
    {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.surveyService = surveyService;
    }


    @RequestMapping( value = "/surveys/edit/{surveyId}", method = RequestMethod.GET )
    public String edit( @PathVariable Long surveyId, Model model, Question question, Answer answer )
    {
        model.addAttribute( "addquestion", question );
        model.addAttribute( "addanswer", answer );
        survey = surveyService.findSurveyById( surveyId, model );
        return "edit";
    }


    @RequestMapping( value = "/surveys/edit/addquestion/", method = RequestMethod.POST )
    public ModelAndView addQuestion( Model model, Question question )
    {
        question.setSurvey( survey );
        questionRepository.save( question );
        return new ModelAndView( "redirect:/surveys/edit/" + survey.getId() );
    }


    @RequestMapping( value = "/surveys/edit/addanswer/{questionId}", method = RequestMethod.POST )
    public ModelAndView addAnswer( @PathVariable Long questionId, Model model, Answer answer )
    {
        findQuestionByQuestionId( questionId, answer );
        answerRepository.save( answer );
        return new ModelAndView( "redirect:/surveys/edit/" + survey.getId() );
    }


    @RequestMapping( value = "/surveys/edit/deletequestion/{questionId}", method = RequestMethod.POST )
    public ModelAndView deleteQuestion( @PathVariable Long questionId, Model model )
    {
        questionRepository.delete( questionId );
        return new ModelAndView( "redirect:/surveys/edit/" + survey.getId() );
    }


    @RequestMapping( value = "/surveys/edit/deleteanswer/{answerId}", method = RequestMethod.POST )
    public ModelAndView deleteAnswer( @PathVariable Long answerId, Model model )
    {
        answerRepository.delete( answerId );
        return new ModelAndView( "redirect:/surveys/edit/" + survey.getId() );
    }


    private void findQuestionByQuestionId( @PathVariable Long questionId, Answer answer )
    {
        List<Question> questions = survey.getQuestions();
        for( Question question :
                        questions )
        {
            if( question.getId().equals( questionId ) )
            {
                answer.setQuestion( question );
            }
        }
    }
}
