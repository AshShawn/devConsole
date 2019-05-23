@echo on
java -cp .\*; org.mybatis.generator.api.ShellRunner -configfile generator.xml -overwrite
pause