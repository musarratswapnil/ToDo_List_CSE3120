package com.example.todo_list.AttendanceCalculator;

public class AttendanceCalculatorLogic {

    private AttendanceAdapter attendanceAdapter;

    public AttendanceCalculatorLogic() {
        this.attendanceAdapter = new AttendanceAdapter();
    }

    public AttendanceCalculatorLogic(AttendanceAdapter attendanceAdapter) {
        this.attendanceAdapter = attendanceAdapter;
    }

    public CalculationResult calculateNeededClasses(int totalCredit, int totalWeeks, int classesAttended, double desiredPercentage, int remainingWeeks) {
        int totalClasses = attendanceAdapter.calculateTotalClasses(totalCredit, totalWeeks);
        int totalRemainingClasses = attendanceAdapter.calculateTotalClasses(totalCredit, remainingWeeks);
        int neededClasses = attendanceAdapter.calculateNeededClasses(totalClasses, desiredPercentage);
        int remainingClassesToAttend = attendanceAdapter.calculateRemainingClassesToAttend(neededClasses, classesAttended);

        CalculationResult result = new CalculationResult();
        result.setTotalRemainingClasses(totalRemainingClasses);

        if (remainingClassesToAttend > totalRemainingClasses) {
            result.setRemainingClassesToAttend(String.valueOf(remainingClassesToAttend));
            result.setMessage(String.format("Sorry, you can't achieve %.2f%% attendance in %d weeks.", desiredPercentage, remainingWeeks));
        } else if (remainingClassesToAttend > 0) {
            result.setRemainingClassesToAttend(String.valueOf(remainingClassesToAttend));
            result.setMessage(String.format("You need to attend %d more classes to achieve %.2f%% attendance.", remainingClassesToAttend, desiredPercentage));
        } else {
            int extraClasses = classesAttended - neededClasses;
            result.setRemainingClassesToAttend("0");
            result.setMessage(String.format("You have already achieved %.2f%% attendance and attended %d more classes than required.", desiredPercentage, extraClasses));
        }

        return result;
    }

    public static class CalculationResult {
        private int totalRemainingClasses;
        private String remainingClassesToAttend;
        private String message;

        public int getTotalRemainingClasses() {
            return totalRemainingClasses;
        }

        public void setTotalRemainingClasses(int totalRemainingClasses) {
            this.totalRemainingClasses = totalRemainingClasses;
        }

        public String getRemainingClassesToAttend() {
            return remainingClassesToAttend;
        }

        public void setRemainingClassesToAttend(String remainingClassesToAttend) {
            this.remainingClassesToAttend = remainingClassesToAttend;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
