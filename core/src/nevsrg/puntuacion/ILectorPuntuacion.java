package nevsrg.puntuacion;

public interface ILectorPuntuacion {
	
	int getComboActual();
	float getPrecision();
    int getCantidadJudges(TipoJudgement judge);
    public int getComboMaximo();
    public int getNotasTotales();
}
