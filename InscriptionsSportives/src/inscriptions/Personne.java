package inscriptions;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.SortNatural;

/**
 * Repr�sente une personne physique pouvant s'inscrire � une comp�tition.
 */

@Entity
public class Personne extends Candidat
{	
	
	private String prenom;

	private String mail;

	private static final long serialVersionUID = 4434646724271327254L;
	
	@OneToMany(mappedBy="personne")
	@Cascade(value = { CascadeType.ALL})
	@SortNatural
	private Set<Equipe> equipes;
	
	@ManyToOne
	@Cascade(value = { CascadeType.SAVE_UPDATE})
	private Equipe equipe;
	
	Personne(Inscriptions inscriptions, String nom, String prenom, String mail)
	{
		super(inscriptions, nom);
		this.prenom = prenom;
		this.mail = mail;
		equipes = new TreeSet<>();
	}

	/**
	 * Retourne le pr�nom de la personne.
	 * @return
	 */
	
	public String getPrenom()
	{
		return prenom;
	}

	/**
	 * Modifie le pr�nom de la personne.
	 * @param prenom
	 */
	
	public void setPrenom(String prenom)
	{
		this.prenom = prenom;
	}

	/**
	 * Retourne l'adresse �lectronique de la personne.
	 * @return
	 */
	
	public String getMail()
	{
		return mail;
	}

	/**
	 * Modifie l'adresse �lectronique de la personne.
	 * @param mail
	 */
	
	public void setMail(String mail)
	{
		this.mail = mail;
	}

	/**
	 * Retoure les �quipes dont cette personne fait partie.
	 * @return
	 */
	
	public Set<Equipe> getEquipes()
	{
		return Collections.unmodifiableSet(equipes);
	}
	
	boolean add(Equipe equipe)
	{
		return equipes.add(equipe);
	}

	boolean remove(Equipe equipe)
	{
		return equipes.remove(equipe);
	}
	
	@Override
	public void delete()
	{
		super.delete();
		for (Equipe e : equipes)
			e.remove(this);
	}
	
	@Override
	public String toString()
	{
		return super.toString() + " membre de " + equipes.toString();
	}
}
