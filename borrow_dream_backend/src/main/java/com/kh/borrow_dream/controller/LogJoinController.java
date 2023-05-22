package com.kh.borrow_dream.controller;

import com.kh.borrow_dream.dao.LogJoinDao;
import com.kh.borrow_dream.vo.LogJoinVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

// xml 설정과 @어노테이션 문법 동일
@CrossOrigin(origins = "http://localhost:3000") // 반드시 넣어줘야하는 영역 / 동일 출처임을 허용해달라는 요청(3000번을 거쳐 8111번 경유)
@RestController // Restful Api 를 사용한다는 의미
public class LogJoinController { // POST : 로그인을 받는 컨트롤러

    // POST : 로그인
    @PostMapping("/login") // axios 의 customLogin 영역과 맞추기 / RequestBody는 axios의 body
    public ResponseEntity<Boolean> customLogin(@RequestBody Map<String, String> loginData) { //응답을 논리형 타입으로 전달하겠다는 의미
        String id = loginData.get("id"); // id(키)를 넣어주면 값을 반환 받는다는 것
        String pwd = loginData.get("pwd");
        System.out.println("ID : " + id);
        System.out.println("PWD : " + pwd);
        LogJoinDao dao = new LogJoinDao(); // 로그인 DAO 라는 새로운 객체 생성
        boolean result =dao.checkLogin(id, pwd);

        return new ResponseEntity<>(result, HttpStatus.OK); // 값을 받는지 테스트 하는 방법(디비까지 가지 않고 프엔과 자바로만 결과값 확인)
    } // 응답을 논리형 타입으로 받음

    // GET 회원조회
    @GetMapping("/custom") // "/고정값 / {변동값}"
    public ResponseEntity<List<LogJoinVo>> customList(@RequestParam String name) { // 리스트 타입으로 vo 값을 가져옴
        System.out.println("NAME : " + name);
        LogJoinDao dao = new LogJoinDao();
        List<LogJoinVo> list = dao.customSelect();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // GET 마이페이지 정보 조회
    @GetMapping("/mypage")
    public ResponseEntity<LogJoinVo> customMypage(@RequestParam String id) {
        System.out.println("ID : " + id);
        LogJoinDao dao = new LogJoinDao();
        LogJoinVo vo = dao.selectOne(id); // // 로그인한 회원의 ID 값을 받아 조회
        return new ResponseEntity<>(vo, HttpStatus.OK);
    }


    // POST : 마이페이지 회원정보 수정 업데이트 조회
    @PostMapping("/mypage/mypageupdate")
    public ResponseEntity<Boolean> mypageUpdate(@RequestBody Map<String, Object> customData) {
        String getPwd = (String) customData.get("pwd");
        String getTel = (String) customData.get("tel");
        String getAddr = (String) customData.get("addr");
        String getId= (String) customData.get("id");

        LogJoinDao dao = new LogJoinDao();
        boolean isTrue = dao.mypageUpdate(getPwd, getTel, getAddr, getId);

        return new ResponseEntity<>(isTrue, HttpStatus.OK);
    }



    // GET : 가입 여부 확인
    @GetMapping("/check")
    public ResponseEntity<Boolean> customCheck(@RequestParam String id) {
        LogJoinDao dao = new LogJoinDao();
        boolean isTrue = dao.customRegCheck(id);
        System.out.println(isTrue);
        return new ResponseEntity<>(isTrue, HttpStatus.OK);
    }


    // GET : 아이디 찾기
    @RequestMapping("/find")
    public ResponseEntity<String> customFind(@RequestParam String name, String email) { // 기존 가입된 id를 불러와야하므로
        System.out.println("이름 : " + name);
        System.out.println("메일 : " + email);
        LogJoinDao dao = new LogJoinDao();
        String getId = dao.findId(name, email);
        return new ResponseEntity<>(getId, HttpStatus.OK);
    }

    // GET : 비밀번호 찾기
    @RequestMapping("/findpwd")
    public ResponseEntity<String> customFindPwd(@RequestParam String id, String email) {
        System.out.println("아이디 : " + id);
        System.out.println("메일 : " + email);
        LogJoinDao dao = new LogJoinDao();
        String getPwd = dao.findPwd(id, email);
        return new ResponseEntity<>(getPwd, HttpStatus.OK);
    }

    // GET : 비밀번호 재설정
//    @RequestMapping("/resetpwd")
//    public ResponseEntity<String> customNewPwd(@RequestParam String id, String pwd) {
//
//    }
//        LogJoinDao dao = new LogJoinDao();
//        int rowsAffected = dao.resetPwd(id, pwd);
//        if (rowsAffected > 0) {
//            return new ResponseEntity<>("비밀번호가 성공적으로 변경되었습니다.", HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("비밀번호 변경에 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


    // POST : 회원 가입
    @PostMapping("/new")
    public ResponseEntity<Boolean> customRegister(@RequestBody Map<String, Object> regData) {
        String getName = (String) regData.get("name");
        String getId = (String) regData.get("id");
        String getPwd = (String) regData.get("pwd");
        String getTel = (String) regData.get("tel");
        String getMail = (String) regData.get("email");
        String getAddr = (String) regData.get("addr");

        System.out.println("Controller : " + getAddr); // 자바 오류 시 출력 시도

        LogJoinDao dao = new LogJoinDao();
        boolean isTrue = dao.customRegister(getName, getId, getPwd, getTel, getMail, getAddr);
        return new ResponseEntity<>(isTrue, HttpStatus.OK);
    }


    // POST : 회원 탈퇴
    @PostMapping("/del")
    public ResponseEntity<Boolean> customDelete(@RequestBody Map<String, String> delData) {
        String getId = delData.get("id");
        LogJoinDao dao = new LogJoinDao();
        boolean isTrue = dao.customDelete(getId);
        return new ResponseEntity<>(isTrue, HttpStatus.OK);
    }
}