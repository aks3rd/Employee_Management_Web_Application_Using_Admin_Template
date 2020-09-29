create view employee_view
as select
employee.employee_id as "employee_id",
employee.name as "name",
employee.gender as "gender",
employee.is_indian as "is_indian",
employee.date_of_birth as "date_of_birth",
employee.basic_salary as "basic_salary",
employee.pan_number as "pan_number",
employee.aadhar_card_number as "aadhar_card_number",
employee.designation_code as "designation_code",
designation.title as "designation_title"
from employee inner join designation on employee.designation_code=designation.code;
