package nevsrg.config;

public interface IJudgeValidator {
    public boolean esJudgeValido(String judge);
    public abstract String[] getJudgesDisponibles();
}