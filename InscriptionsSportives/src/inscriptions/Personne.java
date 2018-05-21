package inscriptions;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.*;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.SortNatural;

import back.Passerelle;

/**
 * Repr�sente une personne physique pouvant s'inscrire � une comp�tition.
 */

@Entity
public class Personne extends Candidat
{	
	
	private static final long serialVersionUID = 4434646724271327254L;
	
	private String prenom;

	private String mail;
	
	@ManyToMany(targetEntity=Equipe.class, mappedBy = "membres", fetch=FetchType.EAGER)
	@Cascade(value = { CascadeType.ALL })
	@SortNatural
	private Set<Equipe> equipes;		
	
	Personne(Inscriptions inscriptions, String nom, String prenom, String mail)
	{
		super(inscriptions, nom);
		this.prenom = prenom;
		this.mail = mail;
		equipes = new TreeSet<>();
	}
	
	public Personne(String nom, String prenom, String mail)
	{
		super(nom);
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
		Passerelle.save(this);
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
		Passerelle.save(this);
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
		equipes.add(equipe);
		Passerelle.save(equipe);
		return equipes.add(equipe);
	}

	boolean remove(Equipe equipe)
	{
		equipes.remove(equipe);
		//Passerelle.delete(equipe);
		return equipes.remove(equipe);
	}
	
	@Override
	public void delete()
	{
		super.delete();
		for (Equipe e : equipes) 
			//Passerelle.delete(this);
			e.remove(this);
	}
	
	@Override
	public String toString()
	{
		return super.toString() + " membre de " + equipes.toString();
	}
}
