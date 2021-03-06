package inscriptions;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;


import javax.persistence.*;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.SortNatural;

import back.Passerelle;

/**
 * Candidat � un �v�nement sportif, soit une personne physique, soit une �quipe.
 *
 */

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Candidat implements Comparable<Candidat>, Serializable
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected int id;
	
	private static final long serialVersionUID = -6035399822298694746L;
	
	@Transient
	private Inscriptions inscriptions;
	
	private String nom;
	
	@ManyToMany(targetEntity=Competition.class, mappedBy = "candidats", fetch=FetchType.EAGER)
	@Cascade(value = { CascadeType.ALL })
	@SortNatural
	private Set<Competition> competitions;
	
	Candidat(Inscriptions inscriptions, String nom)
	{
		this.inscriptions = inscriptions;	
		this.nom = nom;
		competitions = new TreeSet<>();
	}
	
	public Candidat(String nom)
	{
		this.nom = nom;
		competitions = new TreeSet<>();
	}

	
	public int getId() {
		return id;
	}
	
	/**
	 * Retourne le nom du candidat.
	 * @return
	 */
	
	public String getNom()
	{
		return nom;
	}

	/**
	 * Modifie le nom du candidat.
	 * @param nom
	 */
	
	public void setNom(String nom)
	{
		this.nom = nom;
		Passerelle.save(this);
	}

	/**
	 * Retourne toutes les comp�titions auxquelles ce candidat est inscrits.
	 * @return
	 */

	public Set<Competition> getCompetitions()
	{
		return Collections.unmodifiableSet(competitions);
	}
	
	boolean add(Competition competition)
	{
		Passerelle.save(this);
		return competitions.add(competition);
	}

	boolean remove(Competition competition)
	{
		competitions.remove(competition);
//		Passerelle.delete(competition);
		Passerelle.save(this);
		return competitions.remove(competition);
	}

	/**
	 * Supprime un candidat de l'application.
	 */
	
	public void delete()
	{
		for (Competition c : competitions)
			c.remove(this);
		//inscriptions.remove(this);
		Passerelle.delete(this);
	}
	
	@Override
	public int compareTo(Candidat o)
	{
		return getNom().compareTo(o.getNom());
	}
	
	@Override
	public String toString()
	{
		return getNom() + " -> inscrit à " + getCompetitions() + "\n";
	}
}
