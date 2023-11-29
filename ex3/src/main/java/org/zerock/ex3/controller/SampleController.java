package org.zerock.ex3.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.ex3.dto.SampleDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/sample")
@Log4j2
public class SampleController {

    @GetMapping("/ex1")
    public void ex1() {

        log.info("ex1.........");
    }

    /**
     * Thymeleaf 실습을 위해 URL 변경이 용이하게 작성함.
     * SampleDTO 타입의 객체를 20개 추가하고 이를 Model에 담아서 전송.
     * GetMapping의 value 속성을 {}로 처리하면 하나 이상의 URL을 지정할 수 있게 됨.
     * @param model 화면으로 전달되는 모델 데이터
     */
    @GetMapping({"/ex2", "/exLink"})
    public void exModel(Model model) {

        List<SampleDTO> list = IntStream.rangeClosed(1, 20).asLongStream().mapToObj(i -> {
            SampleDTO dto = SampleDTO.builder()
                    .sno(i)
                    .first("First.."+i)
                    .last("Last.."+i)
                    .regTime(LocalDateTime.now())
                    .build();
            return dto;
        }).collect(Collectors.toList());

        model.addAttribute("list", list);
    }

    /**
     * Thymeleaf의 inlihe 속성 사용해보기 - redirect
     * @param redirectAttributes
     * @return
     */
    @GetMapping({"/exInline"})
    public String exInline(RedirectAttributes redirectAttributes) {

        log.info("exInline...........");

        SampleDTO dto = SampleDTO.builder()
                .sno(100L)
                .first("First..100")
                .last("Last..100")
                .regTime(LocalDateTime.now())
                .build();

        redirectAttributes.addFlashAttribute("result", "success");
        redirectAttributes.addFlashAttribute("dto", dto);

        return "redirect:/sample/ex3";
    }

    @GetMapping("/ex3")
    public void ex3() {

        log.info("ex3");

    }

    /**
     * 타임리프 레이아웃 실습
     * th:include - 외부/내부에서 코드 조각(fragment) 가져오기
     */
    @GetMapping({"/exLayout1", "/exLayout2", "/exTemplate", "/exSidebar"})
    public void exLayout1() {

        log.info("exLayout......");

    }

}
