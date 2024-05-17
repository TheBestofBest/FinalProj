package com.app.businessBridge.domain.statistics.dto;

import com.app.businessBridge.domain.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class StatsDto {

    // 전체 직원 수
    private Integer totalPeople;

    // 남성 인원 수
    private Long numberOfMan = 0L;

    // 여성 인원 수
    private Long numberOfWoman = 0L;

    // 20대
    private Long two = 0L;

    // 30대
    private Long three = 0L;

    // 40대
    private Long four = 0L;

    // 50대
    private Long five = 0L;

    // 연봉 2000 이상 ~ 3000 미만
    private Long salaryOne = 0L;

    // 연봉 3000 이상 ~ 4000 미만
    private Long salaryTwo = 0L;

    // 연봉 4000 이상 ~ 5000 미만
    private Long salaryThree = 0L;

    // 연봉 5000 이상 ~ 6000 미만
    private Long salaryFour = 0L;

    // 연봉 6000 이상 ~ 7000 미만
    private Long salaryFive = 0L;

    // 연봉 7000 이상 ~ 8000 미만
    private Long salarySix = 0L;

    // 연봉 8000 이상
    private Long salarySeven = 0L;

    public StatsDto(List<Member> members) {
        this.totalPeople = members.size();

        for(int i = 5 ; i < members.size(); i++) {
            if(members.get(i).getSex() == '남') {
                this.numberOfMan += 1;
            }
            if(members.get(i).getSex() == '여') {
                this.numberOfWoman += 1;
            }
            if(20 <= Integer.valueOf(members.get(i).getAge()) && Integer.valueOf(members.get(i).getAge()) < 30) {
                this.two += 1;
            }
            if(30 <= Integer.valueOf(members.get(i).getAge()) && Integer.valueOf(members.get(i).getAge()) < 40) {
                this.three += 1;
            }
            if(40 <= Integer.valueOf(members.get(i).getAge()) && Integer.valueOf(members.get(i).getAge()) < 50) {
                this.four += 1;
            }
            if(50 <= Integer.valueOf(members.get(i).getAge())) {
                this.five += 1;
            }
            if(20000000 <= members.get(i).getSalary() && members.get(i).getSalary() < 30000000) {
                this.salaryOne += 1;
            }
            if(30000000 <= members.get(i).getSalary() && members.get(i).getSalary() < 40000000) {
                this.salaryTwo += 1;
            }
            if(40000000 <= members.get(i).getSalary() && members.get(i).getSalary() < 50000000) {
                this.salaryThree += 1;
            }
            if(50000000 <= members.get(i).getSalary() && members.get(i).getSalary() < 60000000) {
                this.salaryFour += 1;
            }
            if(60000000 <= members.get(i).getSalary() && members.get(i).getSalary() < 70000000) {
                this.salaryFive += 1;
            }
            if(70000000 <= members.get(i).getSalary() && members.get(i).getSalary() < 80000000) {
                this.salarySix += 1;
            }
            if(80000000 <= members.get(i).getSalary()) {
                this.salarySeven += 1;
            }

        }

    }



}
